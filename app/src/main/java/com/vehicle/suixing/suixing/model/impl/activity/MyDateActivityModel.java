package com.vehicle.suixing.suixing.model.impl.activity;

import android.content.Context;
import com.vehicle.suixing.suixing.util.Log;

import com.vehicle.suixing.suixing.bean.BmobBean.Bmob_date_gasstation;
import com.vehicle.suixing.suixing.callback.BmobListenerWithList;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.model.activity.IMyDateActivityModel;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by KiSoo on 2016/5/16.
 */
public class MyDateActivityModel implements IMyDateActivityModel {
    private final String TAG = "MyDateActivityModel";
    @Override
    public void requestList(Context context, final BmobListenerWithList<Bmob_date_gasstation> listener) {
        BmobQuery<Bmob_date_gasstation> query = new BmobQuery<>();
        query.addWhereEqualTo("app_username", Config.USERNAME);
        query.setLimit(20);
        query.findObjects(context, new FindListener<Bmob_date_gasstation>() {
            @Override
            public void onSuccess(List<Bmob_date_gasstation> list) {
                listener.onSuccess(list);
                Log.d(TAG,"共查到"+list.size()+"条数据");
            }

            @Override
            public void onError(int i, String s) {
                listener.onFailure(i,s);
            }
        });
    }
}
