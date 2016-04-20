package com.vehicle.suixing.suixing.model.fragment;

import android.content.Context;

import com.vehicle.suixing.suixing.callback.BmobListener;
import com.vehicle.suixing.suixing.callback.BmobListenerWithProgress;

/**
 * Created by KiSoo on 2016/4/20.
 */
public interface IMeFragmentModel {
    void  setHead(final Context context, final String head, final BmobListenerWithProgress listener);

    void updateUser(Context context, BmobListener listener);

}
