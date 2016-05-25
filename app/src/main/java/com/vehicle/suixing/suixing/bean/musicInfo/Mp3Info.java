package com.vehicle.suixing.suixing.bean.musicInfo;

/**
 * Created by lenovo on 2016/4/20.
 */
public class Mp3Info {
    private String filename; // 歌曲名称
    private long duration; // 歌曲时长 
    private String singername; // 歌手名称
    private String url; // 歌曲路径
    private String album_name; // 专辑
    private String hash;//hash

    public String getFilename() {
        return filename;
    }

    public void setFilename(String title) {
        this.filename = title;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getSingername() {
        return singername;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
