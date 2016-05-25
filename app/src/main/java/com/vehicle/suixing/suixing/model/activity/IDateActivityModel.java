package com.vehicle.suixing.suixing.model.activity;

import android.content.Context;

import com.vehicle.suixing.suixing.callback.BmobListener;

/**
 * Created by KiSoo on 2016/5/16.
 */
public interface IDateActivityModel {
    void date(Context context, String name, String tel, String time, int position, String amount, String gasTel, String gasLocation, String gasName,BmobListener listener);
}
