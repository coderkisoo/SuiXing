package com.vehicle.suixing.suixing.model;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.app.SuixingApp;
import com.vehicle.suixing.suixing.bean.musicInfo.BmobMusic;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.service.MusicPlayService;
import com.vehicle.suixing.suixing.util.RegisterUtils.SpUtils;

/**
 * Created by KiSoo on 2016/5/14.
 */
@Deprecated
public class MusicPlayView implements View.OnClickListener {
    public static final int ACTION_MUSIC_PLAY = 1001;
    public static final int ACTION_MUSIC_PAUSE = 1002;
    public static final int ACTION_MUSIC_NEXT = 1003;
    public static final int ACTION_MUSIC_LAST = 1004;

    private WindowManager wm;
    private View floatView;
    private WindowManager.LayoutParams params;
    private boolean isShow = false;
    private Dialog musicPlayDialog;
    private Context context;
    private SeekBar sb_music;
    private TextView tv_title;
    private TextView tv_name;
    private ImageView iv_music_play;
    private ImageView iv_next_music;
    private ImageView iv_last_music;
    private MusicPlaytReceiver receiver;
    private boolean play = false;
    private String TAG = "MusicPlayView";

    public MusicPlayView(final Context context) {
        this.context = context;
        final DisplayMetrics metrics = new DisplayMetrics();
        /**********FloatView参数的初始化设置*****************/
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        floatView = View.inflate(context, R.layout.fab_music, null);
        params = new WindowManager.LayoutParams();
        params.gravity = Gravity.TOP + Gravity.LEFT;
        int[] location = SpUtils.getLocation(context);
        params.x = location[0];
        params.y = location[1];
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
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
                        SpUtils.saveLocation(context, params.x, params.y);
                        break;
                }
                return clickable;
            }
        });
        /***********悬浮播放控件的初始化***********/
        /********悬浮播放PlayView的初始化*********/
        View playView = View.inflate(context, R.layout.window_music_play, null);
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
        musicPlayDialog = new Dialog(context);
        musicPlayDialog.setContentView(playView);
        musicPlayDialog.setCanceledOnTouchOutside(false);
        musicPlayDialog.setCanceledOnTouchOutside(true);
        musicPlayDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                showFab();
            }
        });
        floatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissFab(true);
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

    public void dismissFab(boolean show) {
        if (isShow) {
            wm.removeView(floatView);
            isShow = !isShow;
            if (show) {
                musicPlayDialog.show();
            }
        }
    }
    public boolean isShow(){
        return isShow;
    }

    private Messenger messenger = null;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG,"service已经连接");
            messenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG,"service断开连接");
            messenger = null;
        }
    };

    public void unBindSerice() {
        context.unregisterReceiver(receiver);
        context.unbindService(connection);
    }

    public void bindService() {
        //注册BroadCastReceiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(Config.CHANGE_INFO_ACTION);
        filter.addAction(Config.CHANGE_SEEKBAR_ACTION);
        receiver = new MusicPlaytReceiver();
        context.registerReceiver(receiver, filter);
        context.bindService(new Intent(context, MusicPlayService.class), connection, context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        Message message = new Message();
        switch (v.getId()) {
            case R.id.iv_music_play:
                //播放音乐
                message.what = play?ACTION_MUSIC_PAUSE:ACTION_MUSIC_PLAY;
                break;
            case R.id.iv_last_music:
                //上一曲
                message.what = ACTION_MUSIC_NEXT;
                break;
            case R.id.iv_next_music:
                //下一曲
                message.what = ACTION_MUSIC_LAST;
                break;
        }
        try {
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /***
     * BroadCastReceiver
     */
    private class MusicPlaytReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Config.CHANGE_INFO_ACTION:
                    // 获取Intent中的current消息，current代表当前正在播放的歌曲
                    int listPosition = intent.getIntExtra(Config.KEY_CURRENT_POSITION, 0);
                    if (listPosition >= 0) {
                        BmobMusic music = SuixingApp.bmobMusicList.get(listPosition);
                        tv_name.setText(music.getSingername());
                        tv_title.setText(music.getFilename());
                        iv_music_play.setImageResource(R.mipmap.white_pause);
                        play = true;
                    } else {
                        iv_music_play.setImageResource(R.mipmap.white_begin);
                        play = false;
                    }
                    sb_music.setMax(intent.getIntExtra(Config.KEY_MAX_DURATION,0));
                    break;
                case Config.CHANGE_SEEKBAR_ACTION:
                    sb_music.setProgress(intent.getIntExtra(Config.KEY_POSITION, 0));
                    Log.d(TAG,"当前进度为:"+intent.getIntExtra(Config.KEY_POSITION, 0));
                    break;
            }
        }

    }
}
