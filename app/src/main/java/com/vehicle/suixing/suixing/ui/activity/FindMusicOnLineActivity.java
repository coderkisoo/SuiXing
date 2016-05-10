package com.vehicle.suixing.suixing.ui.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.model.info.OnLineMusicInfo;
import com.vehicle.suixing.suixing.service.PlayerService;
import com.vehicle.suixing.suixing.ui.adapter.OnlineMusicListAdapter;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FindMusicOnLineActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    EditText edit_music;
    ImageView bt_query;
    PlayerService playerService;
    TextView text;
    int list_position;
    ListView list_music;
    List<String> music_list,music_hash_list,music_name_list,music_singer_list,music_album_list,music_duration_list;
    List<OnLineMusicInfo> onLineMusicInfos;
    OnlineMusicListAdapter onlineMusicListAdapter;
    OkHttpClient httpClient = new OkHttpClient();
    String get_result_1;
    JsonParser jsonParser = new JsonParser();
    String url_musicMessage = "http://apis.baidu.com/geekery/music/query";
    String arg1 = "s=";
    String arg2 = "&size=10&page=1";
    String url_musicPlayInfo = "http://apis.baidu.com/geekery/music/playinfo";
    String hash_has = "hash=";
    String result_musicPlayInfo;
    String hash;
    String play_url;
    String list_music_duration_true;
    //MediaPlayer player;
    String list_item_music_name,list_item_music_singer;
    public String ONLINE = "ONLINE";
    public String PLAY = "play";
    int list_item_music_duration;
    String music_name,music_name_song;
    String music_singer;
    String music_album;
    String music_hash;
    int music_duration;
    int flag;
    JsonArray data_array;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_findmusic);
        initView();
        onClick();
    }

    private void onClick() {
        bt_query.setOnClickListener(this);
        list_music.setOnItemClickListener(this);
    }

    private String getMusicPlayInfo(String url_musicPlayInfo, String hash_has, String hash) throws IOException {
        Request request = new Request.Builder().url(url_musicPlayInfo+"?"+hash_has+hash)
                .addHeader("apikey","c18a63c08d9b4cc02a2afdf41ccbb606").build();
        Response response = httpClient.newCall(request).execute();
        if (response.isSuccessful()){
            return response.body().string();
        }else {
            return "error";
        }
    }

    private void initView() {
        edit_music = (EditText) findViewById(R.id.edit_music);
        bt_query = (ImageView) findViewById(R.id.bt_query);
        //bt_stop = (Button) findViewById(R.id.bt_stop);
        text = (TextView) findViewById(R.id.text);
        list_music = (ListView) findViewById(R.id.list_music);
        music_list = new ArrayList<>();
        onLineMusicInfos = new ArrayList<>();
        music_hash_list = new ArrayList<>();
        music_duration_list = new ArrayList<>();
        music_album_list = new ArrayList<>();
        music_name_list = new ArrayList<>();
        music_singer_list = new ArrayList<>();
        flag = 1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_query:
                query_music();
                break;
        }
    }

    private void get_music_url() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i<data_array.size();i++){
                    try {
                        hash = music_hash_list.get(i).toString();
                        result_musicPlayInfo = getMusicPlayInfo(url_musicPlayInfo, hash_has, hash);
                        JsonObject object_all = (JsonObject) jsonParser.parse(result_musicPlayInfo);
                        JsonObject data = (JsonObject) object_all.get("data");
                        play_url = data.get("url").getAsString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    music_list.add(play_url);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(FindMusic.this,music_list.toString(),Toast.LENGTH_SHORT).show();
                        for (int i = 0;i<data_array.size();i++){
                            OnLineMusicInfo onLineMusicInfo = new OnLineMusicInfo();
                            onLineMusicInfo.setMusic_name(music_name_list.get(i));
                            onLineMusicInfo.setAlbum_name(music_album_list.get(i));
                            onLineMusicInfo.setDuration(Integer.parseInt(music_duration_list.get(i)));
                            onLineMusicInfo.setSinger_name(music_singer_list.get(i));
                            onLineMusicInfo.setHash(music_hash_list.get(i));
                            onLineMusicInfo.setUrl(music_list.get(i));
                            onLineMusicInfos.add(onLineMusicInfo);
                        }
                        onlineMusicListAdapter = new OnlineMusicListAdapter(FindMusicOnLineActivity.this,onLineMusicInfos);
                        list_music.setAdapter(onlineMusicListAdapter);
                    }
                });
            }
        }).start();
    }

    private void query_music(){
        music_list.clear();
        onLineMusicInfos.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                music_name = edit_music.getText().toString();
                try {
                    get_result_1 = getMessage(url_musicMessage, arg1, music_name, arg2);
                    JsonObject jsonObject_all = (JsonObject) jsonParser.parse(get_result_1);
                    JsonObject data1 = (JsonObject) jsonObject_all.get("data");
                    data_array = (JsonArray) data1.get("data");
                    for (int i = 0;i<data_array.size();i++){
                        JsonObject music = (JsonObject) data_array.get(i);
                        music_name_song = music.get("filename").getAsString();
                        music_singer = music.get("singername").getAsString();
                        music_album = music.get("album_name").getAsString();
                        music_hash = music.get("hash").getAsString();
                        music_duration = music.get("duration").getAsInt();
                        music_hash_list.add(music_hash);
                        music_name_list.add(music_name_song);
                        music_singer_list.add(music_singer);
                        music_album_list.add(music_album);
                        music_duration_list.add(String.valueOf(music_duration));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        get_music_url();
                    }
                });
            }
        }).start();
    }

    private String getMessage(String url_musicMessage, String arg1, String music_name, String arg2) throws IOException{
        String get_result;
        Request request = new Request.Builder().url(url_musicMessage+"?"+arg1+music_name+arg2)
                .addHeader("apikey","c18a63c08d9b4cc02a2afdf41ccbb606").build();
        Response response = httpClient.newCall(request).execute();
        if (response.isSuccessful()){
            get_result = response.body().string();
            return get_result;
        }else {
            return "error";
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        list_item_music_name = onLineMusicInfos.get(position).getMusic_name();
        list_item_music_singer = onLineMusicInfos.get(position).getSinger_name();
        list_item_music_duration = onLineMusicInfos.get(position).getDuration();
        list_music_duration_true = formatTime(list_item_music_duration);
        list_position = position;
        text.setText(" ");
        Intent intent = new Intent(FindMusicOnLineActivity.this,Playing.class);
        intent.putExtra("music_name", list_item_music_name);
        intent.putExtra("music_singer",list_item_music_singer);
        intent.putExtra("music_duration",list_music_duration_true);
        intent.putExtra("list_position",list_position);
        intent.putExtra("list_music", (Serializable) onLineMusicInfos);
        intent.putExtra("list_class",ONLINE);
        intent.putExtra("condition",PLAY);
        intent.putExtra("currentTime",0);
        startActivity(intent);
    }

    /**
     * 对时间格式进行转换，将int型转换为分：秒的形式
     * @param time 传入的时间参数
     * @return
     */
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
