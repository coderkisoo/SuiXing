package com.vehicle.suixing.suixing.model;

import android.content.Context;

import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.callback.BmobListener;

/**
 * Created by KiSoo on 2016/4/20.
 */
public interface IMeFragmentModel {
    void  setHead(final Context context, final String head, final BmobListener listener);

    void updateUser(Context context, BmobListener<User> listener);

}
