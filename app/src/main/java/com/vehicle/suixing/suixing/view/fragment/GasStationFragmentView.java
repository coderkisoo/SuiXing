package com.vehicle.suixing.suixing.view.fragment;

import android.app.Activity;
import android.view.View;

import com.amap.api.maps.AMap;

/**
 * Created by KiSoo on 2016/5/16.
 */
public interface GasStationFragmentView {
    AMap getMap();

    void setNowLocation(String address);


    void showInfo();


    Activity getActivity();

    View getParent();

    void disMissLL();
}
