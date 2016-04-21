package com.vehicle.suixing.suixing.model.info;

/**
 * Created by lenovo on 2016/4/20.
 */
public class Mp3Info {
    public long id; // 歌曲ID 3
    public String title; // 歌曲名称 0
    public String album; // 专辑 7
    public long albumId;//专辑ID 6
    public String displayName; //显示名称 4
    public String artist; // 歌手名称 2
    public long duration; // 歌曲时长 1
    public long size; // 歌曲大小 8
    public String url; // 歌曲路径 5
    public String lrcTitle; // 歌词名称
    public String lrcSize; // 歌词大小
    public Mp3Info() {
        super();
    }
    public Mp3Info(long id, String title, String album, long albumId,
                   String displayName, String artist, long duration, long size,
                   String url, String lrcTitle, String lrcSize) {
        super();
        this.id = id;
        this.title = title;
        this.album = album;
        this.albumId = albumId;
        this.displayName = displayName;
        this.artist = artist;
        this.duration = duration;
        this.size = size;
        this.url = url;
        this.lrcTitle = lrcTitle;
        this.lrcSize = lrcSize;
    }
    @Override
    public String toString() {
        return "Mp3Info [id=" + id + ", title=" + title + ", album=" + album
                + ", albumId=" + albumId + ", displayName=" + displayName
                + ", artist=" + artist + ", duration=" + duration + ", size="
                + size + ", url=" + url + ", lrcTitle=" + lrcTitle
                + ", lrcSize=" + lrcSize + "]";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }



    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLrcTitle() {
        return lrcTitle;
    }

    public void setLrcTitle(String lrcTitle) {
        this.lrcTitle = lrcTitle;
    }

    public String getLrcSize() {
        return lrcSize;
    }

    public void setLrcSize(String lrcSize) {
        this.lrcSize = lrcSize;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
