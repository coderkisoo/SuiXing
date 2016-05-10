package com.vehicle.suixing.suixing.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.vehicle.suixing.suixing.model.info.OnLineMusicInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/4/27.
 */
public class OnLineMusicPlayService extends Service {
    String TAG = "OnLineMusicPlayService";
    List<OnLineMusicInfo> onLineMusicInfos;
    int position;
    int currentTime;
    PlayerService playerService;
    MediaPlayer mediaPlayer;
    onLineMusicReceiver onlineMusicReceiver;
    String play_url;
    boolean stop = false;
    String list_class;
    String MUSIC_DURATION_ONLINE = "MUSIC_DURATION_ONLINE";
    String MUSIC_CURRENT_ONLINE = "MUSIC_CURRENT_ONLINE";

    private Handler handler_progress = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (mediaPlayer != null) {
                    if (list_class.equals("ONLINE")){
                        currentTime = mediaPlayer.getCurrentPosition(); // 获取当前音乐播放的位置
                        Intent intent = new Intent();
                        intent.setAction(MUSIC_CURRENT_ONLINE);
                        intent.putExtra("currentTime_online", currentTime);
                        sendBroadcast(intent); // 给PlayerActivity发送广播
                        handler_progress.sendEmptyMessageDelayed(1, 1000);
                    }
                }

            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        init();
        super.onCreate();
    }


    private void init() {
        onLineMusicInfos = new ArrayList<>();
        playerService = new PlayerService();
        onlineMusicReceiver = new onLineMusicReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("PLAY_MUSIC");
        intentFilter.addAction("CONTINUE_MUSIC");
        intentFilter.addAction("PAUSE_MUSIC");
        intentFilter.addAction("NEXT_MUSIC");
        intentFilter.addAction("LAST_MUSIC");
        intentFilter.addAction("DOWNLOAD_MUSIC");
        registerReceiver(onlineMusicReceiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if (onLineMusicInfos != null) {
            onLineMusicInfos.clear();
        }
        onLineMusicInfos = (List<OnLineMusicInfo>) bundle.getSerializable("online_music_list");
        position = bundle.getInt("position");
        list_class = bundle.getString("list_class");
        play_url = onLineMusicInfos.get(position).getUrl();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
        mediaPlayer = new MediaPlayer();
        setOnCompletionListener();
        play(0);
        return super.onStartCommand(intent, flags, startId);
    }

    private void setOnCompletionListener() {
        if (onLineMusicInfos != null) {
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (position == onLineMusicInfos.size()-1){
                        position = 0;
                    }else {
                        position++;
                    }
                    if (mp != null) {
                        if (mp.isPlaying()) {
                            mp.stop();
                            mp.release();
                        }
                        play_url = onLineMusicInfos.get(position).getUrl();
                        try {
                            mp.reset();
                            mp.setDataSource(play_url);
                            mp.prepare();
                            mp.start();
                            mp.setOnPreparedListener(new PreparedListener(currentTime));// 注册一个监听器
                            handler_progress.sendEmptyMessage(1);
                            stop = false;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

    }

    private class onLineMusicReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "PLAY_MUSIC":
                    play(0);
                    break;
                case "NEXT_MUSIC":
                    next();
                    break;
                case "LAST_MUSIC":
                    last();
                    break;
                case "DOWNLOAD_MUSIC":
                    download();
                    break;
                case "CONTINUE_MUSIC":
                    if (stop){
                        resume();
                    }else {
                        pause();
                    }
                    break;

            }
        }
    }

    private void resume() {
        Log.i(TAG, "click continue.......");
        mediaPlayer.start();
        stop = false;
    }

    private void download() {
        Toast.makeText(OnLineMusicPlayService.this, "下载", Toast.LENGTH_SHORT).show();
    }

    private void last() {
        //Toast.makeText(OnLineMusicPlayService.this, "上一曲", Toast.LENGTH_SHORT).show();
        if (position==0){
            position = (onLineMusicInfos.size()-1);
        }else {
            position--;
        }
        play_url = onLineMusicInfos.get(position).getUrl();
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(play_url);
            mediaPlayer.prepare();
            mediaPlayer.start();
            stop = false;
            mediaPlayer.setOnPreparedListener(new PreparedListener(currentTime));// 注册一个监听器
            handler_progress.sendEmptyMessage(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void next() {
        if (mediaPlayer!=null)
            if (mediaPlayer.isPlaying()){
                mediaPlayer.reset();
            }
        if (position==(onLineMusicInfos.size()-1)){
            position = 0;
        }else {
            position++;
        }
        play_url = onLineMusicInfos.get(position).getUrl();
        try {
            mediaPlayer.setDataSource(play_url);
            mediaPlayer.prepare();
            mediaPlayer.start();
            stop = false;
            mediaPlayer.setOnPreparedListener(new PreparedListener(0));// 注册一个监听器
            handler_progress.sendEmptyMessage(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pause() {
        Log.i(TAG, "click music_pause..........");
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            stop = true;
        }
    }

    private void play(int currentTime) {
        Log.i(TAG, "click play.........");
        stop = false;
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(play_url);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnPreparedListener(new PreparedListener(currentTime));// 注册一个监听器
            handler_progress.sendEmptyMessage(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 实现一个OnPrepareLister接口,当音乐准备好的时候开始播放
     */
    private final class PreparedListener implements MediaPlayer.OnPreparedListener {
        private int currentTime;
        public PreparedListener(int currentTime) {
            this.currentTime = currentTime;
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            Log.i(TAG, "接口............");
            mediaPlayer.start(); // 开始播放
            if (currentTime > 0) { // 如果音乐不是从头播放
                mediaPlayer.seekTo(currentTime);
            }
            Intent intent_duration = new Intent();
            intent_duration.setAction(MUSIC_DURATION_ONLINE);
            int duration = mediaPlayer.getDuration();
            intent_duration.putExtra("duration", duration);    //通过Intent来传递歌曲的总长度
            sendBroadcast(intent_duration);
        }
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        unregisterReceiver(onlineMusicReceiver);
        stopSelf();
        super.onDestroy();
    }
}