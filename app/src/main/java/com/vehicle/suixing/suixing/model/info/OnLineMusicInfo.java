package com.vehicle.suixing.suixing.model.info;

import java.io.Serializable;

/**
 * Created by lenovo on 2016/4/25.
 */
public class OnLineMusicInfo implements Serializable{
    public String music_name;    //音乐名
    public String singer_name;   //歌手名
    public String album_name;    //专辑名
    public String hash;          //hash值
    public int duration;         //时长
    public String url;

    public OnLineMusicInfo() {
        super();
    }

    @Override
    public String toString() {
        return "OnLineMusicInfo{" +
                "music_name='" + music_name + '\'' +
                ", singer_name='" + singer_name + '\'' +
                ", album_name='" + album_name + '\'' +
                ", hash='" + hash + '\'' +
                ", duration=" + duration +
                '}';
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMusic_name() {
        return music_name;
    }

    public void setMusic_name(String music_name) {
        this.music_name = music_name;
    }

    public String getSinger_name() {
        return singer_name;
    }

    public void setSinger_name(String singer_name) {
        this.singer_name = singer_name;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
