package com.vehicle.suixing.suixing.model.activity;

import android.content.Context;

import com.vehicle.suixing.suixing.bean.BmobBean.VehicleImage;
import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.callback.BmobListener;
import com.vehicle.suixing.suixing.callback.BmobListenerWithList;

/**
 * Created by KiSoo on 2016/4/20.
 */
public interface IAddSuccessActivityModel {
    void saveOnInternet(Context context,VehicleInformation info,BmobListener listener);
    void startDownLoad(Context context,VehicleInformation info,BmobListenerWithList<VehicleImage> listener);
}
