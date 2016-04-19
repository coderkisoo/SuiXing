package com.vehicle.suixing.suixing.model;

import android.content.Context;

import com.vehicle.suixing.suixing.bean.BmobBean.VehicleImage;
import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.callback.BmobListener;

/**
 * Created by KiSoo on 2016/4/20.
 */
public interface IAddSuccessActivityModel {
    void saveOnInternet(Context context,VehicleInformation info,BmobListener<VehicleImage> listener);
    void startDownLoad(Context context,VehicleInformation info,BmobListener listener);
}
