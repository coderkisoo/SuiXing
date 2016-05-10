package com.vehicle.suixing.suixing.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.app.AppConstant;
import com.vehicle.suixing.suixing.model.info.Mp3Info;
import com.vehicle.suixing.suixing.model.info.OnLineMusicInfo;
import com.vehicle.suixing.suixing.service.OnLineMusicPlayService;
import com.vehicle.suixing.suixing.service.PlayerService;
import com.vehicle.suixing.suixing.util.MediaUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2015/12/11.
 */
public class Playing extends Activity{
    String TAG= "Playing";
    private TextView songV,artistV;
    private Button playBtn,nextBtn,preBtn,repBtn,myLove;
    private SeekBar seekBar;
    private TextView currentProgress; // 当前进度消耗的时间
    private TextView finalProgress; // 歌曲时间
    private TextView playing_music_name;
    private int flag; // 播放标识
    private static List<OnLineMusicInfo> onLineMusicInfos;
    Intent intent;
    String play_url;
    String list_class;
    private String title; // 歌曲标题
    private String artist; // 歌曲艺术家
    private String url; // 歌曲路径
    private String condition;
    private int listPosition; // 播放歌曲在mp3Infos的位置
    private int list_position_online;
    private int currentTime; // 当前歌曲播放时间
    private int duration; // 歌曲长度
    int music_position;

    private int repeatState;
    private final int isCurrentRepeat = 1; // 单曲循环
    private final int isAllRepeat = 2; // 全部循环
    private final int isNoneRepeat = 3; // 无重复播放
    private boolean isPlaying; // 正在播放
    private boolean isPause; // 暂停
    private boolean isNoneShuffle; // 顺序播放
    private boolean isShuffle; // 随机播放

    private List<Mp3Info> mp3Infos;
    private PlayerReceiver playerReceiver;
    private OnLinePlayerReceiver onLinePlayerReceiver;
    public static final String UPDATE_ACTION = "UPDATE_ACTION"; // 更新动作
    public static final String CTL_ACTION = "CTL_ACTION"; // 控制动作
    public static final String MUSIC_CURRENT = "MUSIC_CURRENT"; // 音乐当前时间改变动作
    public static final String MUSIC_DURATION = "MUSIC_DURATION";// 音乐播放长度改变动作
    public static final String MUSIC_PLAYING = "MUSIC_PLAYING"; // 音乐正在播放动作
    public static final String REPEAT_ACTION = "REPEAT_ACTION"; // 音乐重复播放动作
    public static final String SHUFFLE_ACTION = "SHUFFLE_ACTION";// 音乐随机播放动作

    String PLAY_MUSIC = "PLAY_MUSIC";
    String CONTINUE_MUSIC = "CONTINUE_MUSIC";
    String LAST_MUSIC = "LAST_MUSIC";
    String NEXT_MUSIC = "NEXT_MUSIC";
    String PAUSE_MUSIC = "PAUSE_MUSIC";
    String DOWNLOAD_MUSIC = "DOWNLOAD_MUSIC";
    /**
     * 播放音乐
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playing);

        findView();
        setViewOnclickListener();
        repeatState=isNoneRepeat;
        mp3Infos = MediaUtil.getMp3Infos(Playing.this);	//获取所有音乐的集合对象
        registerReceiver();
        Intent intent_1 = getIntent();
        list_class = intent_1.getStringExtra("list_class");
//        condition = intent_1.getStringExtra("condition");
        condition = "music_pause";
        if (list_class.equals("ONLINE")){
            getIntentTwo(intent_1);
            //Toast.makeText(this,list_class+"1",Toast.LENGTH_SHORT).show();
        }else if (list_class.equals("LOCAL")){
            getDataFromBundle(intent_1);
            initView();
            //Toast.makeText(this,list_class+"2",Toast.LENGTH_SHORT).show();
            //初始化视图
        }
    }
    private void registerReceiver() {
        //定义和注册广播接收器
        playerReceiver = new PlayerReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPDATE_ACTION);
        filter.addAction(MUSIC_CURRENT);
        filter.addAction(MUSIC_DURATION);
        registerReceiver(playerReceiver, filter);
        onLinePlayerReceiver = new OnLinePlayerReceiver();
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("PAUSE");
        filter1.addAction("PLAY");
        filter1.addAction("MUSIC_DURATION_ONLINE");
        filter1.addAction("MUSIC_CURRENT_ONLINE");
        filter1.addAction("RESUME");
        registerReceiver(onLinePlayerReceiver,filter1);
    }
    /**
     * 从界面上根据id获取按钮
     */
    private void findView() {
        songV = (TextView) findViewById(R.id.songV);
        //artistV = (TextView) findViewById(R.id.artistV);
        preBtn= (Button) findViewById(R.id.preBtn);
        //repBtn = (Button) findViewById(R.id.repBtn);
        playBtn = (Button) findViewById(R.id.playBtn);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        currentProgress = (TextView) findViewById(R.id.currentProgress);
        finalProgress = (TextView) findViewById(R.id.finalProgress);
        //playing_music_name = (TextView) findViewById(R.id.playing_music_name);
        //returnBtn=(Button)findViewById(R.id.returnBtn);
        //myLove = (Button) findViewById(R.id.myLove_playing);
    }
    /**
     * 给每一个按钮设置监听器
     */
    private void setViewOnclickListener() {
        ViewOnclickListener ViewOnClickListener = new ViewOnclickListener();
        preBtn.setOnClickListener(ViewOnClickListener);
        //repBtn.setOnClickListener(ViewOnClickListener);
        playBtn.setOnClickListener(ViewOnClickListener);
        nextBtn.setOnClickListener(ViewOnClickListener);
        seekBar.setOnSeekBarChangeListener(new SeekBarChangeListener());
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    /**
     * 从Bundle中获取来自HomeActivity中传过来的数据
     */
    private void getDataFromBundle(Intent intent) {
        Bundle bundle = intent.getExtras();
        title = bundle.getString("title");
        artist = bundle.getString("artist");
        url = bundle.getString("url");
        listPosition = bundle.getInt("listPosition");
        repeatState = bundle.getInt("repeatState");
        isShuffle = bundle.getBoolean("shuffleState");
        flag = bundle.getInt("MSG");
        currentTime = bundle.getInt("currentTime");
        duration =bundle.getInt("duration");
        isPlaying=bundle.getBoolean("isPlaying");
        isPause=bundle.getBoolean("isPause");
    }
    /**
     * 初始化界面
     */
    public void initView(){
        if(isPlaying){
            playBtn.setText("music_pause");
        }else if(isPause) {playBtn.setText("play");}
        songV.setText(title);
        artistV.setText(artist);
        seekBar.setProgress(currentTime);
        seekBar.setMax(duration);
        finalProgress.setText(MediaUtil.formatTime(mp3Infos.get(
                listPosition).getDuration()));
        if (flag == AppConstant.PlayerMsg.PLAYING_MSG) { // 如果播放信息是正在播放
            Intent intent = new Intent(Playing.this,PlayerService
                    .class);
            intent.putExtra("listPosition", listPosition);
            sendBroadcast(intent);
        } else if (flag == AppConstant.PlayerMsg.PLAY_MSG) { // 如果是点击列表播放歌曲的话
            play();
        } else if (flag == AppConstant.PlayerMsg.CONTINUE_MSG) {
            Intent intent = new Intent(Playing.this, PlayerService.class);
            playBtn.setText("music_pause");
            intent.setAction("MUSIC_SERVICE");
            intent.putExtra("MSG", AppConstant.PlayerMsg.CONTINUE_MSG);	//继续播放音乐
            startService(intent);
        }else if (flag == AppConstant.PlayerMsg.PLAY_MSG_ON){
            repeat_none();
            Intent intent = new Intent(Playing.this,PlayerReceiver.class);
            intent.setAction("MUSIC_SERVICE");
            intent.putExtra("url",play_url);
            intent.putExtra("listPosition", list_position_online);
            intent.putExtra("MSG", flag);
            startService(intent);
        }
    }
    /**
     * 反注册广播
     */
    @Override
    protected void onStop() {

        super.onStop();
    }
    @Override
    protected void onDestroy() {
        unregisterReceiver(playerReceiver);
        unregisterReceiver(onLinePlayerReceiver);
        super.onDestroy();
    }
    /**
     * 拿到intent传递来的数据
     */
    public void getIntentTwo(Intent intent) {
        if (onLineMusicInfos!=null){
            onLineMusicInfos.clear();
        }
        finalProgress.setText("");
        String music_name = intent.getStringExtra("music_name");
        String music_singer = intent.getStringExtra("music_singer");
        String music_duration =intent.getStringExtra("music_duration");
        music_position = intent.getIntExtra("list_position", -1);
        Bundle bundle = intent.getExtras();
        onLineMusicInfos = (List<OnLineMusicInfo>) bundle.getSerializable("list_music");
        //playing_music_name.setText(music_name);
        //songV.setText(music_name);
        //artistV.setText(music_singer);
        currentTime = bundle.getInt("currentTime");
        duration =onLineMusicInfos.get(music_position).getDuration();
        finalProgress.setText(music_duration);
        sendMessageToService(music_position,onLineMusicInfos);
    }
    private void sendMessageToService(int music_position, List<OnLineMusicInfo> onLineMusicInfos) {
        intent  = new Intent(Playing.this,OnLineMusicPlayService.class);
        intent.putExtra("position",music_position);
        intent.putExtra("online_music_list", (Serializable) onLineMusicInfos);
        intent.putExtra("list_class",list_class);
        startService(intent);
    }
    /**
     * 控件点击事件
     *
     * @author wwj
     *
     */
    private class ViewOnclickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (list_class.equals("LOCAL")){
                Intent intent_local = new Intent(Playing.this,PlayerService.class);
                intent_local.putExtra("list_class",list_class);
                switch (v.getId()) {
                    case R.id.playBtn:
                        if (isPlaying) {
                            playBtn.setText("play");
                            intent_local.setAction("MUSIC_SERVICE");
                            intent_local.putExtra("listPosition",listPosition);
                            intent_local.putExtra("MSG", AppConstant.PlayerMsg.PAUSE_MSG);
                            startService(intent_local);
                            isPlaying = false;
                            isPause = true;
                        } else if (isPause) {
                            playBtn.setText("music_pause");
                            intent_local.setAction("MUSIC_SERVICE");
                            intent_local.putExtra("listPosition",listPosition);
                            intent_local.putExtra("MSG", AppConstant.PlayerMsg.CONTINUE_MSG);
                            startService(intent_local);
                            isPause = false;
                            isPlaying = true;
                        }
                        break;
                    case R.id.preBtn: // 上一首歌曲
                        previous();
                        break;
                    case R.id.nextBtn: // 下一首歌曲
                        next();
                        break;
                }
            }else if (list_class.equals("ONLINE")){
                switch (v.getId()) {
                    case R.id.playBtn:
                        Intent intent_condition = new Intent();
                        intent_condition.setAction(CONTINUE_MUSIC);
                        sendBroadcast(intent_condition);
                        Log.i(TAG,"点击播放暂停发送广播"+intent.getAction());
                        break;
                    case R.id.preBtn: // 上一首歌曲
                        if (music_position==0){
                            music_position = (onLineMusicInfos.size()-1);
                        }else {
                            music_position--;
                        }
                        currentProgress.setText(MediaUtil.formatTime(0));
                        seekBar.setProgress(currentTime);
                        //playing_music_name.setText(onLineMusicInfos.get(music_position).getMusic_name());
                        songV.setText(onLineMusicInfos.get(music_position).getMusic_name());
                        artistV.setText(onLineMusicInfos.get(music_position).getSinger_name());
                        finalProgress.setText(formatTime(onLineMusicInfos.get(music_position).getDuration()));
                        Intent intent_last = new Intent();
                        intent_last.setAction(LAST_MUSIC);
                        sendBroadcast(intent_last);
                        break;
                    case R.id.nextBtn: // 下一首歌曲
                        if (music_position==(onLineMusicInfos.size()-1)){
                            music_position = 0;
                        }else {
                            music_position++;
                        }
                        currentProgress.setText(MediaUtil.formatTime(0));
                        seekBar.setProgress(currentTime);
                        //playing_music_name.setText(onLineMusicInfos.get(music_position).getMusic_name());
                        songV.setText(onLineMusicInfos.get(music_position).getMusic_name());
                        artistV.setText(onLineMusicInfos.get(music_position).getSinger_name());
                        finalProgress.setText(formatTime(onLineMusicInfos.get(music_position).getDuration()));
                        Intent intent_next = new Intent();
                        intent_next.setAction(NEXT_MUSIC);
                        sendBroadcast(intent_next);
                        break;
                }
            }
        }
    }
    /**
     * 实现监听Seekbar的类
     *
     * @author wwj
     *
     */
    private class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            switch(seekBar.getId()) {
                case R.id.seekBar:
                    if (fromUser) {
                        audioTrackChange(progress); // 用户控制进度的改变
                    }
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }
    /**
     * 播放进度改变
     *
     */
    public void audioTrackChange(int progress) {
        if (list_class.equals("LOCAL")){
            Intent intent = new Intent(Playing.this,PlayerService.class);
            intent.setAction("MUSIC_SERVICE");
            intent.putExtra("url", url);
            intent.putExtra("listPosition", listPosition);
            intent.putExtra("MSG", AppConstant.PlayerMsg.PROGRESS_CHANGE);
            intent.putExtra("progress", progress);
            startService(intent);
        }else if (list_class.equals("ONLINE")){
            Intent intent = new Intent(Playing.this,OnLineMusicPlayService.class);
            intent.setAction("PLAY_MUSIC");
            intent.putExtra("MSG", "PROGRESS_CHANGE");
            intent.putExtra("progress", progress);
            sendBroadcast(intent);
        }
    }
    /**
     * 单曲循环
     */
    public void repeat_one() {
        Intent intent = new Intent(CTL_ACTION);
        intent.putExtra("control", 1);
        sendBroadcast(intent);
    }
    /**
     * 全部循环
     */
    public void repeat_all() {
        Intent intent = new Intent(CTL_ACTION);
        intent.putExtra("control", 2);
        sendBroadcast(intent);
    }
    /**
     * 顺序播放
     */
    public void repeat_none() {
        Intent intent = new Intent(CTL_ACTION);
        intent.putExtra("control", 3);
        sendBroadcast(intent);
    }
    /**
     * 随机播放
     */
    public void shuffleMusic() {
        Intent intent = new Intent(CTL_ACTION);
        intent.putExtra("control", 4);
        sendBroadcast(intent);
    }
    /**
     * 用来接收从service传回来的广播的内部类currentProgress.setText(MediaUtil.formatTime(currentTime));
     */
    public class PlayerReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(MUSIC_CURRENT)) {
                currentTime = intent.getIntExtra("currentTime", -1);
                currentProgress.setText(MediaUtil.formatTime(currentTime));
                seekBar.setProgress(currentTime);
            } else if (action.equals(MUSIC_DURATION)) {
                int duration = intent.getIntExtra("duration", -1);
                seekBar.setMax(duration);
                finalProgress.setText(MediaUtil.formatTime(duration));
            } else if (action.equals(UPDATE_ACTION)) {
                // 获取Intent中的current消息，current代表当前正在播放的歌曲
                listPosition = intent.getIntExtra("current", -1);
                url = mp3Infos.get(listPosition).getUrl();
                if (listPosition >= 0) {
                    songV.setText(mp3Infos.get(listPosition).getTitle());
                    artistV.setText(mp3Infos.get(listPosition).getArtist());
                }
                if (listPosition == 0) {
                    finalProgress.setText(MediaUtil.formatTime(mp3Infos.get(
                            listPosition).getDuration()));
                    playBtn.setText("play");
                    isPause = true;
                }
            }
        }
    }

    /**
     * 用来接收online的广播
     */
    public class OnLinePlayerReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, intent.getAction()+"收到的广播...");
            String action = intent.getAction();
            if (action.equals("PAUSE")){
                condition.replace("continue","music_pause");
                Log.i(TAG, "set condition music_pause  ............................................");
            }else if (action.equals("PLAY")){
                condition = "play";
                Log.i(TAG, "set condition play  ............................................");
            }else if (action.equals("MUSIC_DURATION_ONLINE")){
                int duration = intent.getIntExtra("duration", -1);
                seekBar.setMax(duration);
            }else if (action.equals("MUSIC_CURRENT_ONLINE")){
                currentTime = intent.getIntExtra("currentTime_online", -1);
                currentProgress.setText(MediaUtil.formatTime(currentTime));
                seekBar.setProgress(currentTime);
            }else if (action.equals("RESUME")){
                condition.replace("music_pause","continue");
                Log.i(TAG, "set condition continue  ............................................");
            }
        }
    }
    /**
     * 播放音乐
     */
    public void play() {
        // 开始播放的时候为顺序播放
        repeat_none();
        Intent intent = new Intent(Playing.this,PlayerReceiver.class);
        intent.setAction("MUSIC_SERVICE");
        intent.putExtra("url", url);
        intent.putExtra("listPosition", listPosition);
        intent.putExtra("MSG", flag);
        startService(intent);
    }
    /**
     * 上一首
     */
    public void previous() {
        if(isPause){
            playBtn.setText("music_pause");
            isPlaying=true;
            isPause=false;
        }else if(isPlaying){
            playBtn.setText("music_pause");
            isPause=false;
            isPlaying=true;
        }
        listPosition = listPosition - 1;
        if (listPosition >= 0) {
            Mp3Info mp3Info = mp3Infos.get(listPosition); // 上一首MP3
            songV.setText(mp3Info.getTitle());
            artistV.setText(mp3Info.getArtist());
            url = mp3Info.getUrl();
            Intent intent = new Intent(Playing.this,PlayerService.class);
            intent.setAction("MUSIC_SERVICE");
            intent.putExtra("url", mp3Info.getUrl());
            intent.putExtra("listPosition", listPosition);
            intent.putExtra("MSG", AppConstant.PlayerMsg.PRIVIOUS_MSG);
            //intent.putExtra("isPlaying", isPlaying);
            //intent.putExtra("isPause",isPause);
            //intent.setAction("UPDATE_ACTION");
            startService(intent);
        } else {
            listPosition = 0;
            Toast.makeText(Playing.this, "没有上一首了", Toast.LENGTH_SHORT)
                    .show();
        }
    }
    /**
     * 下一首
     */
    public void next() {
        if(isPause){
            playBtn.setText("music_pause");
            isPlaying=true;
            isPause=false;
        }else if(isPlaying){
            playBtn.setText("music_pause");
            isPause=false;
            isPlaying=true;
        }
        listPosition = listPosition + 1;
        if (listPosition <= mp3Infos.size() - 1) {
            Mp3Info mp3Info = mp3Infos.get(listPosition);
            url = mp3Info.getUrl();
            songV.setText(mp3Info.getTitle());
            artistV.setText(mp3Info.getArtist());
            Intent intent = new Intent(Playing.this,PlayerService.class);
            // intent.putExtra("isPlaying",isPlaying);
            // intent.putExtra("isPause",isPause);
            // intent.setAction("UPDATE_ACTION");
            intent.setAction("MUSIC_SERVICE");
            intent.putExtra("url", mp3Info.getUrl());
            intent.putExtra("listPosition", listPosition);
            intent.putExtra("MSG", AppConstant.PlayerMsg.NEXT_MSG);
            startService(intent);

        } else {
            listPosition = mp3Infos.size() - 1;
            Toast.makeText(Playing.this, "没有下一首了", Toast.LENGTH_SHORT)
                    .show();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        //System.out.println("PlayerActivity has paused");
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    public static String formatTime(long time) {
        String min = time / (60) + "";
        String sec = time % (60) + "";
        if (min.length() < 2) {
            min = "0" + time / (60) + "";
        } else {
            min = time / (60) + "";
        }
        if (sec.length()<2) {
            sec = "0" + (time % (60)) + "";
        } else {
            sec = (time % (60)) + "";
        }
        return min + ":" + sec.trim().substring(0, 2);
    }
}