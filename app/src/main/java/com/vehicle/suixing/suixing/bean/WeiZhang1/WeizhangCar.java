package com.vehicle.suixing.suixing.bean.WeiZhang1;

import java.util.List;

/**
 * Created by KiSoo on 2016/4/7.
 */
public class WeizhangCar {
    private String lsprefix;
    private String lsnum;
    private String carorg;
    private String usercarid;
    private List<WeizhangDate> list;

    public String getLsprefix() {
        return lsprefix;
    }

    public void setLsprefix(String lsprefix) {
        this.lsprefix = lsprefix;
    }

    public String getLsnum() {
        return lsnum;
    }

    public void setLsnum(String lsnum) {
        this.lsnum = lsnum;
    }

    public String getCarorg() {
        return carorg;
    }

    public void setCarorg(String carorg) {
        this.carorg = carorg;
    }

    public String getUsercarid() {
        return usercarid;
    }

    public void setUsercarid(String usercarid) {
        this.usercarid = usercarid;
    }

    public List<WeizhangDate> getList() {
        return list;
    }

    public void setList(List<WeizhangDate> list) {
        this.list = list;
    }
}
