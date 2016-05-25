package com.vehicle.suixing.suixing.util.musicUtil;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.vehicle.suixing.suixing.app.SuixingApp;
import com.vehicle.suixing.suixing.bean.musicInfo.Mp3Info;
import com.vehicle.suixing.suixing.common.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovo on 2016/4/20.
 */
public class MediaUtil {
    private static List<String> audioList = new ArrayList<String>(); // 要播放的音频列表


    public static List<Mp3Info> getCurrentList(){
        return Config.isLocal ? SuixingApp.mp3Infos : SuixingApp.onLineInfos;
    }

    private static String[] imageFormatSet = new String[] { "mp3", "wav", "3gp" }; // 合法的音频文件格式
    // 判断是否为音频文件
    private static boolean isAudioFile(String path) {
        for (String format : imageFormatSet) { // 遍历数组
            if (path.contains(format)) { // 判断是否为有合法的音频文件
                return true;
            }
        }
        return false;
    }
    /**
     * 用于从数据库中查询歌曲的信息，保存在List当中
     *
     * @return
     */

    public static List<Mp3Info> getMp3Infos(Context context) {
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        List<Mp3Info> mp3Infos = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            Mp3Info mp3Info = new Mp3Info();
           String title = cursor.getString((cursor
                    .getColumnIndex(MediaStore.Audio.Media.TITLE))); // 音乐标题
            String artist = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ARTIST)); // 艺术家
            String album = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ALBUM)); // 专辑
            long duration = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DURATION)); // 时长
             String url = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DATA)); // 文件路径
            int isMusic = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Media.IS_MUSIC)); // 是否为音乐

            if (isMusic != 0) { // 只把音乐添加到集合当中
                mp3Info.setFilename(title);
                mp3Info.setSingername(artist);
                mp3Info.setAlbum_name(album);
                mp3Info.setDuration(duration);
                mp3Info.setUrl(url);
                mp3Infos.add(mp3Info);
            }
        }
        return mp3Infos;
    }

    /**
     * 往List集合中添加Map对象数据，每一个Map对象存放一首音乐的所有属性
     *
     * @param mp3Infos
     * @return
     */
    public static List<HashMap<String, String>> getMusicMaps(
            List<Mp3Info> mp3Infos) {
        List<HashMap<String, String>> mp3list = new ArrayList<HashMap<String, String>>();
        for (Mp3Info mp3Info : mp3Infos) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("title", mp3Info.getFilename());
            map.put("Artist", mp3Info.getSingername());
            map.put("album", mp3Info.getAlbum_name());
            map.put("duration", formatTime(mp3Info.getDuration()));
            map.put("url", mp3Info.getUrl());
            mp3list.add(map);
        }
        return mp3list;
    }

    /**
     * 格式化时间，将毫秒转换为分:秒格式
     *
     * @param time
     * @return
     */
    public static String formatTime(long time) {
        String min = time / (1000 * 60) + "";
        String sec = time % (1000 * 60) + "";
        if (min.length() < 2) {
            min = "0" + time / (1000 * 60) + "";
        } else {
            min = time / (1000 * 60) + "";
        }
        if (sec.length() == 4) {
            sec = "0" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 3) {
            sec = "00" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 2) {
            sec = "000" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 1) {
            sec = "0000" + (time % (1000 * 60)) + "";
        }
        return min + ":" + sec.trim().substring(0, 2);
    }
}
