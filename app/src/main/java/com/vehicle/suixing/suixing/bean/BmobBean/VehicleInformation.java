package com.vehicle.suixing.suixing.bean.BmobBean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by KiSoo on 2016/3/21.
 */
public class VehicleInformation extends BmobObject implements Serializable {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    private String isYours;//判断是否是车的二维码
    private String name;//车名
    private String num;//车牌号
    private String framenum;//车架号
    private String percent;//汽油百分比
    private String size;//车身级别
    private String mileage;//车行驶的里程
    private String model;//发动机型号
    private String function;//发动机性能
    private String speed;//变速器性能
    private String light;//车灯状况
    private String url;
    private int maxMileage;//最大提醒

    public int getMaxMileage() {
        return maxMileage;
    }

    public void setMaxMileage(int maxMileage) {
        this.maxMileage = maxMileage;
    }

    public String getFramenum() {
        return framenum;
    }

    public void setFramenum(String framenum) {
        while (framenum.length() < 6) {
            framenum = 0 + framenum;
        }
        this.framenum = framenum;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsYours() {
        return isYours;
    }

    public void setIsYours(String isYours) {
        this.isYours = isYours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        while (model.length() < 4) {
            model = 0 + model;
        }
        this.model = model;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }


}
