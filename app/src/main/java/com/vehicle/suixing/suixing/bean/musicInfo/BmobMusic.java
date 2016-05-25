package com.vehicle.suixing.suixing.bean.musicInfo;

import android.net.Uri;

/**
 * Created by KiSoo on 2016/5/14.
 */
public class BmobMusic {
    private String filename; // 歌曲名称
    private String singername; // 歌手名称
    private Uri url; // 歌曲路径

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSingername() {
        return singername;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public Uri getUri() {
        return url;
    }

    public void setUrl(Uri url) {
        this.url = url;
    }
}
