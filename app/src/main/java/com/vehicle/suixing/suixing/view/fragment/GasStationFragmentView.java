package com.vehicle.suixing.suixing.view.fragment;

import android.view.View;

import com.baidu.mapapi.map.BaiduMap;

/**
 * Created by KiSoo on 2016/5/16.
 */
public interface GasStationFragmentView {
    BaiduMap getMap();

    void setNowLocation(String address);

    View getParent();
}
