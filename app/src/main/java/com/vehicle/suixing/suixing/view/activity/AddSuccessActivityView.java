package com.vehicle.suixing.suixing.view.activity;

import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.view.BaseView;

/**
 * Created by KiSoo on 2016/4/4.
 */
public interface AddSuccessActivityView extends BaseView {
    VehicleInformation getInformation();
    void finish();
    void launched();
    void launch();
    void tvAddSuccess();
    void displayImg(String url);
    void setName(String name);
    void setNumber(String number);
    void launchFailed();
}
