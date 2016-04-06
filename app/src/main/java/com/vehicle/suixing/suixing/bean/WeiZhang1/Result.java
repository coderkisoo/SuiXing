package com.vehicle.suixing.suixing.bean.WeiZhang1;

import java.util.List;

/**
 * Created by KiSoo on 2016/4/6.
 */
public class Result {
    private List<Province> data;
    private String updatetime;
    private String updatemsg;

    public List<Province> getData() {
        return data;
    }

    public void setData(List<Province> data) {
        this.data = data;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdatemsg() {
        return updatemsg;
    }

    public void setUpdatemsg(String updatemsg) {
        this.updatemsg = updatemsg;
    }
}
