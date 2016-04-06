package com.vehicle.suixing.suixing.bean.WeiZhang;

import java.util.List;

/**
 * Created by KiSoo on 2016/4/5.
 */
@Deprecated
public class WeizhangResponseHistoryJson {
    private String status;
    private String total_score;
    private String total_money;
    private String count;
    private List<Historys> historys;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal_score() {
        return total_score;
    }

    public void setTotal_score(String total_score) {
        this.total_score = total_score;
    }

    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<Historys> getHistorys() {
        return historys;
    }

    public void setHistorys(List<Historys> historys) {
        this.historys = historys;
    }
}
