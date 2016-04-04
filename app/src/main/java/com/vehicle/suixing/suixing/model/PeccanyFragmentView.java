package com.vehicle.suixing.suixing.model;

import android.widget.PopupWindow;

/**
 * Created by KiSoo on 2016/4/4.
 */
public interface PeccanyFragmentView{
    void showWindow(PopupWindow window);
    int getWidth();
    int getHeight();
    void setVehicle(String name);
}
