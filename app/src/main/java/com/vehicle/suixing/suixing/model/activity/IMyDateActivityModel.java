package com.vehicle.suixing.suixing.model.activity;

import android.content.Context;

import com.vehicle.suixing.suixing.bean.BmobBean.Bmob_date_gasstation;
import com.vehicle.suixing.suixing.callback.BmobListenerWithList;

/**
 * Created by KiSoo on 2016/5/16.
 */
public interface IMyDateActivityModel {
    void requestList(Context context, BmobListenerWithList<Bmob_date_gasstation> listener);
}
