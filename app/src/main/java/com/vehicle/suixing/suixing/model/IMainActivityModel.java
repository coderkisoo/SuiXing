package com.vehicle.suixing.suixing.model;

import android.content.Context;

import com.vehicle.suixing.suixing.callback.BmobListener;
import com.vehicle.suixing.suixing.callback.UpdateList;

/**
 * Created by KiSoo on 2016/4/20.
 */
public interface IMainActivityModel {
    void initUser(Context context,UpdateList listener,BmobListener bmobListener);
}
