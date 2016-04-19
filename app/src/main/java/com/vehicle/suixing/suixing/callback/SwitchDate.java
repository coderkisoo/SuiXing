package com.vehicle.suixing.suixing.callback;

import com.vehicle.suixing.suixing.bean.WeiZhang1.WeizhangDate;

import java.util.List;

/**
 * Created by KiSoo on 2016/4/20.
 */
public interface SwitchDate {
    void getPast(List<WeizhangDate> past);
    void getNow(List<WeizhangDate> now);
}
