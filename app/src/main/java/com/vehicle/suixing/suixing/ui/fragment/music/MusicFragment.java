package com.vehicle.suixing.suixing.ui.fragment.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.app.AppConstant;
import com.vehicle.suixing.suixing.manager.DBManager;
import com.vehicle.suixing.suixing.model.info.Mp3Info;
import com.vehicle.suixing.suixing.presenter.VehicleInfoFragmentPresenter;
import com.vehicle.suixing.suixing.service.PlayerService;
import com.vehicle.suixing.suixing.ui.activity.MainActivity;
import com.vehicle.suixing.suixing.ui.activity.Playing;
import com.vehicle.suixing.suixing.ui.adapter.MusicListAdapter;
import com.vehicle.suixing.suixing.util.MediaUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by lenovo on 2016/4/20.
 */
public class MusicFragment extends Fragment {
    private View view;
    private ListAdapter listAdapter; // 自定义列表适配器
    private ImageButton getSong;
    private ImageButton playingBtn;
    private ImageButton playBtn;
    private SeekBar seekBar1;
    private Button nextBtn; // 下一首
    private List<Mp3Info> mp3Infos = null;
    private Intent intent;
    private TextView musicTitle,musicArtist;// 歌曲标题
    private ListView listView;
    private ArrayAdapter transformerArrayAdapter;
    private ArrayList<String> transformerList = new ArrayList<String>();
    private int repeatState; // 循环标识
    private final int isCurrentRepeat = 1; // 单曲循环
    private final int isAllRepeat = 2; // 全部循环
    private final int isNoneRepeat = 3; // 无重复播放
    private boolean isNoneShuffle = true; // 顺序播放
    private boolean isShuffle = false; // 随机播放
    private boolean isFirstTime =true;
    private boolean isPlaying; // 正在播放
    private boolean isPause; // 暂停
    private int listPosition = 0; // 标识列表位置
    private HomeReceiver homeReceiver; // 自定义的广播接收器
    /*
    一系列动作
     */
    public static final String UPDATE_ACTION = "UPDATE_ACTION"; // 更新动作
    public static final String CTL_ACTION = "CTL_ACTION"; // 控制动作
    public static final String MUSIC_CURRENT = "MUSIC_CURRENT"; // 当前音乐改变动作
    public static final String MUSIC_DURATION = "MUSIC_DURATION"; // 音乐时长改变动作
    public static final String REPEAT_ACTION = "REPEAT_ACTION"; // 音乐重复改变动作
    public static final String SHUFFLE_ACTION = "SHUFFLE_ACTION"; // 音乐随机播放动作
    private int currentTime; // 当前时间
    private int duration; // 时长
    private DBManager db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_music, null);
        ButterKnife.bind(this, view);
        initView();
        onClick();
        return view;
    }

    private void onClick() {
        listView.setOnItemClickListener(new MusicListItemClickListener());
        seekBar1.setOnSeekBarChangeListener(new SeekBarChangeListener());
        seekBar1.setMax(100);
        getSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showgetMp3List();
            }
        });
        ViewOnClickListener viewOnClickListener = new ViewOnClickListener();
        playBtn.setOnClickListener(viewOnClickListener);
        playingBtn.setOnClickListener(viewOnClickListener);
        nextBtn.setOnClickListener(viewOnClickListener);
        seekBar1.setOnSeekBarChangeListener(new SeekBarChangeListener());
    }

    private void initView() {
        playingBtn=(ImageButton)view.findViewById(R.id.playingBtn);
        musicTitle=(TextView)view.findViewById(R.id.songView);
        musicArtist=(TextView)view.findViewById(R.id.artistView);
        playBtn = (ImageButton)view.findViewById(R.id.playBtn);
        seekBar1=(SeekBar)view.findViewById(R.id.seekBar1);
        getSong=(ImageButton)view.findViewById(R.id.getSong);
        listView = (ListView) view.findViewById(R.id.mMusiclist);
        nextBtn = (Button)view.findViewById(R.id.nextBtn);
        db=new DBManager(getActivity());
        transformerArrayAdapter = new ArrayAdapter(getContext(), R.layout.adapter_transformer, transformerList);
        seekBar1.setProgress(currentTime);
        seekBar1.setMax(duration);
        repeatState = isNoneRepeat;
        homeReceiver = new HomeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPDATE_ACTION);
        filter.addAction(MUSIC_CURRENT);
        filter.addAction(MUSIC_DURATION);
        filter.addAction(REPEAT_ACTION);
        filter.addAction(SHUFFLE_ACTION);
        getActivity().registerReceiver(homeReceiver, filter);
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
                case R.id.seekBar1:
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
        Intent intent = new Intent(getContext(),PlayerService.class);
        intent.setAction("MUSIC_SERVICE");
        //   intent.putExtra("url", url);
        intent.putExtra("listPosition", listPosition);
        intent.putExtra("MSG", AppConstant.PlayerMsg.PROGRESS_CHANGE);
        intent.putExtra("progress", progress);
        getContext().startService(intent);
    }

    private class MusicListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            listPosition = position; // 获取列表点击的位置
            playMusic(listPosition); // 播放音乐
        }
    }

    private class ViewOnClickListener implements View.OnClickListener {
        Intent intent = new Intent(getContext(),PlayerService.class);

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.playBtn: // 播放音乐
                    if(mp3Infos!=null){
                        if (isFirstTime) {
                            play();
                            isFirstTime=false;
                            isPlaying=true;
                            isPause=false;
                        }
                        else {
                            if (isPlaying) {
                                intent.setAction("MUSIC_SERVICE");
                                intent.putExtra("MSG", AppConstant.PlayerMsg.PAUSE_MSG);
                                getContext().startService(intent);
                                isPlaying = false;
                                isPause = true;
                            } else if (isPause) {
                                intent.setAction("MUSIC_SERVICE");
                                intent.putExtra("MSG",
                                        AppConstant.PlayerMsg.CONTINUE_MSG);
                                getContext().startService(intent);
                                isPause = false;
                                isPlaying = true;
                            }
                        }
                        break;}else {
                        Toast.makeText(getContext(), "请先导入歌曲", Toast.LENGTH_SHORT).show();}
                case R.id.nextBtn: // 下一首
                    if(mp3Infos!=null){
                        isFirstTime = false;
                        isPlaying = true;
                        isPause = false;
                        next();
                        break;}else {Toast.makeText(getContext(),"请先导入歌曲",Toast.LENGTH_SHORT).show();}
                case R.id.playingBtn://我的音乐
                    if(mp3Infos!=null){
                        Mp3Info mp3Info = mp3Infos.get(listPosition);
                        Intent intent = new Intent(getContext(),Playing.class);
                        intent.putExtra("title", mp3Info.getTitle());
                        intent.putExtra("url", mp3Info.getUrl());
                        intent.putExtra("artist", mp3Info.getArtist());
                        intent.putExtra("listPosition", listPosition);
                        intent.putExtra("currentTime", currentTime);
                        intent.putExtra("duration", duration);
                        intent.putExtra("MSG", AppConstant.PlayerMsg.PLAYING_MSG);
                        intent.putExtra("isPlaying",isPlaying);
                        intent.putExtra("isPause",isPause);
                        startActivity(intent);
                        break;}else{
                        Toast.makeText(getContext(),"请先导入歌曲",Toast.LENGTH_SHORT).show();
                    }


            }

        }
    }

    private void showgetMp3List(){
        mp3Infos =null;
        ArrayList<Mp3Info>  m1=db.searchAllData1();
        if(m1.size()<1){
            mp3Infos = MediaUtil.getMp3Infos(getContext()); // 获取歌曲对象集合
            db.add1(mp3Infos);
            m1=db.searchAllData1();
        }
        mp3Infos=m1;
        listAdapter = new MusicListAdapter(getContext(), m1);
        listView.setAdapter(listAdapter);
    }

    public void playMusic(int listPosition) {
        if (mp3Infos != null) {
            Mp3Info mp3Info = mp3Infos.get(listPosition);
            musicTitle.setText(mp3Info.getTitle()); // 这里显示标题
            musicArtist.setText(mp3Info.getArtist());//歌手
            isFirstTime=false;
            isPlaying=true;
            isPause=false;
            Intent intent = new Intent(getActivity(), Playing.class); // 定义Intent对象，跳转到PlayerActivity
            // 添加一系列要传递的数据
            intent.putExtra("title", mp3Info.getTitle());
            intent.putExtra("url", mp3Info.getUrl());
            intent.putExtra("artist", mp3Info.getArtist());
            intent.putExtra("listPosition", listPosition);
            intent.putExtra("currentTime", currentTime);
            intent.putExtra("repeatState",repeatState);
            intent.putExtra("shuffleState", isShuffle);
            intent.putExtra("duration", mp3Info.getDuration());
            intent.putExtra("MSG", AppConstant.PlayerMsg.PLAY_MSG);
            intent.putExtra("isPlaying",isPlaying);
            intent.putExtra("isPause",isPause);
            startActivity(intent);
            play();
        }
    }

    public void play() {
        // playBtn.setBackground(getDrawable(R.drawable.beijing));
        Mp3Info mp3Info = mp3Infos.get(listPosition);
        musicTitle.setText(mp3Info.getTitle());
        musicArtist.setText(mp3Info.getArtist());//歌手
        Intent intent = new Intent(getContext(),PlayerService.class);
        intent.setAction("MUSIC_SERVICE");
        intent.putExtra("listPosition", 0);
        intent.putExtra("url", mp3Info.getUrl());
        intent.putExtra("MSG", AppConstant.PlayerMsg.PLAY_MSG);
        getContext().startService(intent);}

    public void next() {
        listPosition = listPosition + 1;

        if (listPosition <= mp3Infos.size() - 1) {
            Mp3Info mp3Info = mp3Infos.get(listPosition);
            musicTitle.setText(mp3Info.getTitle());
            musicArtist.setText(mp3Info.getArtist());//歌手
            Intent intent = new Intent(getContext(),PlayerService.class);
            intent.setAction("MUSIC_SERVICE");
            intent.putExtra("listPosition", listPosition);
            intent.putExtra("url", mp3Info.getUrl());
            intent.putExtra("MSG", AppConstant.PlayerMsg.NEXT_MSG);
            getContext().startService(intent);
        } else {
            listPosition = mp3Infos.size() - 1;
            Toast.makeText(getContext(), "没有下一首了", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public class HomeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(MUSIC_CURRENT)) {
                // currentTime代表当前播放的时间
                currentTime = intent.getIntExtra("currentTime", -1);
                seekBar1.setProgress(currentTime);
                //  m.setText(MediaUtil.formatTime(currentTime));
            } else if (action.equals(MUSIC_DURATION)) {
                duration = intent.getIntExtra("duration", -1);
                seekBar1.setMax(duration);
            } else if (action.equals(UPDATE_ACTION)) {
                // 获取Intent中的current消息，current代表当前正在播放的歌曲
                listPosition = intent.getIntExtra("current", -1);
                if (listPosition >= 0) {
                    musicTitle.setText(mp3Infos.get(listPosition).getTitle());
                    musicArtist.setText(mp3Infos.get(listPosition).getArtist());
                }
            } else if (action.equals(REPEAT_ACTION)) {
                repeatState = intent.getIntExtra("repeatState", -1);
                switch (repeatState) {
                    case isCurrentRepeat: // 单曲循环
                        break;
                    case isAllRepeat: // 全部循环
                        break;
                    case isNoneRepeat: // 无重复
                        break;
                }
            } else if (action.equals(SHUFFLE_ACTION)) {
                isShuffle = intent.getBooleanExtra("shuffleState", false);
                if (isShuffle) {
                    isNoneShuffle = false;

                } else {
                    isNoneShuffle = true;

                }
            }
        }

    }
}
