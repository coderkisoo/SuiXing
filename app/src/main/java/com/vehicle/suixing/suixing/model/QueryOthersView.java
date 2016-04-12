package com.vehicle.suixing.suixing.model;

import android.widget.PopupWindow;

/**
 * Created by KiSoo on 2016/4/9.
 */
public interface QueryOthersView {
    void showProvinceWindow(PopupWindow window);

    void showCity(String city);

    void showCityWindow(PopupWindow window);

    void showProvince(String province);

    int getWidth();

    int getHeight();

    void dismissWindow(PopupWindow window);

    String getFrameNum();

    String getVehicleNum();

    String getEngineNum();

    void showProgress();

    void dismissProgress();

    void showError(String msg);
}
