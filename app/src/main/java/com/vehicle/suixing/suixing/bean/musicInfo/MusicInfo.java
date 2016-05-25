package com.vehicle.suixing.suixing.bean.musicInfo;

import java.util.List;

/**
 * Created by KiSoo on 2016/5/13.
 */
public class MusicInfo {
    private int current_page;
    private String keyword;
    private int total_rows;
    private int total_page;
    private int page_size;
    private List<Mp3Info> data;

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public void setTotal_rows(int total_rows) {
        this.total_rows = total_rows;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public List<Mp3Info> getData() {
        return data;
    }

    public void setData(List<Mp3Info> data) {
        this.data = data;
    }
}
