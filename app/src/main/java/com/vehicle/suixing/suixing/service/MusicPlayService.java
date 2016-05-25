package com.vehicle.suixing.suixing.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

import com.vehicle.suixing.suixing.app.SuixingApp;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.model.MusicPlayView;

/**
 * Created by KiSoo on 2016/5/12.
 */
public class MusicPlayService extends Service {
    private final String TAG = "MusicPlayService";
    private MediaPlayer player;
    private static int currentPosition = 0;//列表当前位置
    private boolean isFirstPlay = true;
    private final Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //利用msg.obj来判断播放还是暂停
            switch (msg.what) {
                case MusicPlayView.ACTION_MUSIC_PLAY:
                    play(isFirstPlay);
                    break;
                case MusicPlayView.ACTION_MUSIC_PAUSE:
                    pause();
                    break;
                case MusicPlayView.ACTION_MUSIC_LAST:
                    currentPosition--;
                    play(true);
                    break;
                case MusicPlayView.ACTION_MUSIC_NEXT:
                    currentPosition++;
                    play(true);
                    break;
            }
            super.handleMessage(msg);
        }
    });

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "Service已经启动");
        player = new MediaPlayer();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                currentPosition++;
                play(true);
            }
        });
        player.stop();
        new SeekBarChange().start();
        super.onCreate();
    }

    /***
     * 播放音乐
     */
    private void play(boolean reset) {
        isFirstPlay = false;
        //如果当前列表的position大于列表的总数据数
        if (currentPosition > SuixingApp.bmobMusicList.size() - 1) {
            currentPosition = 0;
        }
        if (currentPosition < 0) {
            currentPosition = 6;
        }
        if (reset) {
            player.reset();
            player = MediaPlayer.create(this, SuixingApp.bmobMusicList.get(currentPosition).getUri());
        }
        player.start();
        //发送携带当前歌曲列表位置的广播
        sendBroadcast(new Intent().setAction(Config.CHANGE_INFO_ACTION).putExtra(Config.KEY_CURRENT_POSITION, currentPosition).putExtra(Config.KEY_MAX_DURATION,player.getDuration()));
    }

    /***
     * 暂停
     */
    private void pause() {
        player.pause();
        sendBroadcast(new Intent().setAction(Config.CHANGE_INFO_ACTION)
                .putExtra(Config.KEY_CURRENT_POSITION, -1));
    }

    @Override
    public boolean onUnbind(Intent intent) {
        pause();
        return super.onUnbind(intent);
    }

    private class SeekBarChange extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    Log.d(TAG,"发送出进度:"+player.getCurrentPosition());
                    sendBroadcast(new Intent().setAction(Config.CHANGE_SEEKBAR_ACTION).putExtra(Config.KEY_POSITION, player.getCurrentPosition()));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
