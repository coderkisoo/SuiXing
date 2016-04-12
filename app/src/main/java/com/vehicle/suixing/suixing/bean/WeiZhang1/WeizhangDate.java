package com.vehicle.suixing.suixing.bean.WeiZhang1;

import java.io.Serializable;

/**
 * Created by KiSoo on 2016/4/7.
 */
public class WeizhangDate implements Serializable{
    private String time;
    private String address;
    private String content;
    private String legalnum;
    private String price;
    private String agency;
    private String score;
    private String illegalid;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLegalnum() {
        return legalnum;
    }

    public void setLegalnum(String legalnum) {
        this.legalnum = legalnum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getIllegalid() {
        return illegalid;
    }

    public void setIllegalid(String illegalid) {
        this.illegalid = illegalid;
    }
}
