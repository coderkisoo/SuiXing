package com.vehicle.suixing.suixing.model.impl.activity;

import android.content.Context;
import android.widget.Toast;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.BmobBean.Bmob_date_gasstation;
import com.vehicle.suixing.suixing.callback.BmobListener;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.model.activity.IDateActivityModel;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by KiSoo on 2016/5/16.
 */
public class DateActivityModel implements IDateActivityModel {

    @Override
    public void date(final Context context, final String name, final String tel,
                     final String time, final int position, final String amount,
                     final String gasTel, final String gasLocation, final String gasName,
                     final BmobListener listener) {
        if (name.equals("")) {
            Toast.makeText(context, "请输入您的姓名", Toast.LENGTH_SHORT).show();
            listener.onFailure(1, "");
            return;
        }
        if (tel.length() == 0) {
            Toast.makeText(context, "请输入电话号", Toast.LENGTH_SHORT).show();
            listener.onFailure(1, "");
            return;
        }
        if (time.equals("")) {
            Toast.makeText(context, "请输入您的姓名", Toast.LENGTH_SHORT).show();
            listener.onFailure(1, "");
            return;
        }
        if (position == -1) {
            Toast.makeText(context, "请选择汽油型号", Toast.LENGTH_SHORT).show();
            listener.onFailure(1, "");
            return;
        }
        Bmob_date_gasstation date_gasstation = new Bmob_date_gasstation();
        date_gasstation.setDate_uerName(name);
        date_gasstation.setDate_uerPhonNum(tel);
        date_gasstation.setDate_time(time);
        date_gasstation.setDate_oilStyle(context.getResources().getStringArray(R.array.oilStyle)[position]);
        date_gasstation.setDate_money(amount);
        date_gasstation.setGasstation_name(gasName);
        date_gasstation.setGasstation_phoneNum(gasTel);
        date_gasstation.setApp_username(Config.USERNAME);
        date_gasstation.setGasstation_drass(gasLocation);
        date_gasstation.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                listener.onSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                listener.onFailure(i, s);
            }
        });
    }
}
