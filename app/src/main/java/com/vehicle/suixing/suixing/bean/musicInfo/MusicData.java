package com.vehicle.suixing.suixing.bean.musicInfo;

/**
 * Created by KiSoo on 2016/5/13.
 */
public class MusicData {
    private int code;
    private String status;
    private String msg;
    private MusicInfo data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MusicInfo getData() {
        return data;
    }

    public void setData(MusicInfo data) {
        this.data = data;
    }
}
