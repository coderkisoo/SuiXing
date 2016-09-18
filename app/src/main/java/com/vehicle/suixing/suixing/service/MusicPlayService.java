package com.vehicle.suixing.suixing.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.app.SuixingApp;
import com.vehicle.suixing.suixing.bean.musicInfo.BmobMusic;
import com.vehicle.suixing.suixing.util.Log;
import com.vehicle.suixing.suixing.util.RegisterUtils.SpUtils;
import com.vehicle.suixing.suixing.util.formatUtils.DensityUtil;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by KiSoo on 2016/5/12.
 */
public class MusicPlayService extends Service {
    private MediaPlayer player;
    private MusicPlayView playView;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private static int currentPosition = new Random().nextInt(6);//列表当前位置
    private boolean isFirstPlay = true;
    private final String TAG = "MusicPlayService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        player = new MediaPlayer();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                currentPosition++;
                play(true);
            }
        });
        player.stop();
        playView = new MusicPlayView();
        playView.showFab();
        super.onCreate();
    }

    public class MusicPlayView implements View.OnClickListener {

        private WindowManager wm;
        private View floatView;
        private WindowManager.LayoutParams params;
        private boolean isShow = false;
        private AlertDialog musicPlayDialog;
        private SeekBar sb_music;
        private TextView tv_title;
        private TextView tv_name;
        private ImageView iv_music_play;
        private ImageView iv_next_music;
        private ImageView iv_last_music;

        public MusicPlayView() {
            final DisplayMetrics metrics = new DisplayMetrics();
            /**********FloatView参数的初始化设置*****************/
            wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            floatView = View.inflate(MusicPlayService.this, R.layout.fab_music, null);
            params = new WindowManager.LayoutParams();
            params.gravity = Gravity.TOP + Gravity.LEFT;

            int[] location = SpUtils.getLocation(MusicPlayService.this);
            params.x = location[0];
            params.y = location[1];
            params.width = DensityUtil.dip2px(MusicPlayService.this, 72);
            params.height = DensityUtil.dip2px(MusicPlayService.this, 72);
            params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
            params.format = PixelFormat.TRANSLUCENT;
            params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
            //可以拖动的
            floatView.setOnTouchListener(new View.OnTouchListener() {
                int startX = 0;
                int startY = 0;
                boolean clickable = false;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            startX = (int) event.getRawX();
                            startY = (int) event.getRawY();
                            clickable = false;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            int dX = (int) event.getRawX() - startX;
                            int dY = (int) event.getRawY() - startY;
                            params.x += dX;
                            params.y += dY;
                            //屏蔽非法拖动
                            if (params.x < 0) {
                                params.x = 0;
                            }
                            if (params.y < 0) {
                                params.y = 0;
                            }
                            if (params.x > wm.getDefaultDisplay().getWidth() - floatView.getWidth()) {
                                params.x = wm.getDefaultDisplay().getWidth() - floatView.getWidth();
                            }
                            if (params.y > wm.getDefaultDisplay().getHeight() - floatView.getHeight()) {
                                params.y = wm.getDefaultDisplay().getHeight() - floatView.getHeight();
                            }
                            wm.updateViewLayout(floatView, params);
                            startX = (int) event.getRawX();
                            startY = (int) event.getRawY();
                            clickable = true;
                            break;
                        case MotionEvent.ACTION_UP:
                            params.x = params.x > (metrics.widthPixels - params.width) / 2 ? metrics.widthPixels - params.width : 0;
                            wm.updateViewLayout(floatView, params);
                            SpUtils.saveLocation(MusicPlayService.this, params.x, params.y);
                            break;
                    }
                    return clickable;
                }
            });
            /***********悬浮播放控件的初始化***********/
            /********悬浮播放PlayView的初始化*********/
            View playView = View.inflate(MusicPlayService.this, R.layout.window_music_play, null);
            sb_music = (SeekBar) playView.findViewById(R.id.sb_music);
            tv_title = (TextView) playView.findViewById(R.id.tv_title);
            tv_name = (TextView) playView.findViewById(R.id.tv_name);
            iv_music_play = (ImageView) playView.findViewById(R.id.iv_music_play);
            iv_next_music = (ImageView) playView.findViewById(R.id.iv_next_music);
            iv_last_music = (ImageView) playView.findViewById(R.id.iv_last_music);
            iv_music_play.setOnClickListener(this);
            iv_next_music.setOnClickListener(this);
            iv_last_music.setOnClickListener(this);
            sb_music.setEnabled(false);
            /**************悬浮控件Dialog****************/
            AlertDialog.Builder builder = new AlertDialog.Builder(MusicPlayService.this);
            musicPlayDialog = builder.create();
            musicPlayDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            musicPlayDialog.setCanceledOnTouchOutside(true);
            musicPlayDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    showFab();
                }
            });
            musicPlayDialog.show();
            musicPlayDialog.setContentView(playView);
            floatView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissFab();
                }
            });
        }

        public void showFab() {
            if (!isShow) {
                wm.addView(floatView, params);
                isShow = !isShow;
                musicPlayDialog.dismiss();
            }
        }

        //dismiss floatbutton
        public void dismissFab() {
            if (isShow) {
                wm.removeView(floatView);
                isShow = !isShow;
                musicPlayDialog.show();
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_music_play:
                    //播放音乐
                    if (null == player)
                        player = new MediaPlayer();
                    if (player.isPlaying()) {
                        player.pause();
                        iv_music_play.setImageResource(R.mipmap.white_begin);
                    } else {
                        tv_name.setText("正在获取歌曲信息中...");
                        tv_title.setText("获取信息ing...");
                        play(isFirstPlay);
                    }
                    break;
                case R.id.iv_last_music:
                    //上一曲
                    tv_name.setText("正在获取歌曲信息中...");
                    tv_title.setText("获取信息ing...");
                    currentPosition--;
                    play(true);
                    break;
                case R.id.iv_next_music:
                    //下一曲
                    tv_name.setText("正在获取歌曲信息中...");
                    tv_title.setText("获取信息ing...");
                    currentPosition++;
                    play(true);
                    break;
            }
        }

        public void setProgress(int progress) {
            sb_music.setProgress(progress);
        }

        public void changeInfo() {
            BmobMusic music = SuixingApp.bmobMusicList.get(currentPosition);
            tv_name.setText(music.getSingername());
            tv_title.setText(music.getFilename());
            iv_music_play.setImageResource(R.mipmap.white_pause);
            sb_music.setMax(player.getDuration());
        }
    }

    /***
     * 播放音乐,将reset MediaPlayer的任务放在子线程，更新，绘制UI放在主线程
     */

    private void play(final boolean reset) {
        Observable
                .create(new Observable.OnSubscribe<Boolean>() {
                    @Override
                    public void call(Subscriber<? super Boolean> subscriber) {
                        subscriber.onNext(true);
                        if (reset) {
                            player.reset();
                            player = MediaPlayer.create(MusicPlayService.this, SuixingApp.bmobMusicList.get(currentPosition).getUri());
                        }
                        player.start();
                        if (player.isPlaying()) {
                            mTimer = null;
                            mTimerTask = null;
                            mTimer = new Timer();
                            mTimerTask = new TimerTask() {
                                @Override
                                public void run() {
                                    playView.setProgress(player.getCurrentPosition());
                                }
                            };
                            mTimer.schedule(mTimerTask, 0, 10);
                            subscriber.onCompleted();
                        }
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        playView.changeInfo();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MusicPlayService.this, "加载失败", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean bBoolean) {
                        isFirstPlay = false;
                        //如果当前列表的position大于列表的总数据数
                        if (currentPosition > SuixingApp.bmobMusicList.size() - 1) {
                            currentPosition = 0;
                        }
                        if (currentPosition < 0) {
                            currentPosition = 6;
                        }
                    }
                });
    }
}
