package com.vehicle.suixing.suixing.presenter.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.callback.BmobListener;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.model.activity.IDateActivityModel;
import com.vehicle.suixing.suixing.model.impl.activity.DateActivityModel;
import com.vehicle.suixing.suixing.ui.activity.DateSuccessActivity;
import com.vehicle.suixing.suixing.ui.activity.MyDateActivity;
import com.vehicle.suixing.suixing.util.DialogUtils.ProgressDialogUtils;
import com.vehicle.suixing.suixing.util.RegisterUtils.BmobError;
import com.vehicle.suixing.suixing.util.formatUtils.JsonUtils;
import com.vehicle.suixing.suixing.view.activity.DateActivityView;

/**
 * Created by KiSoo on 2016/5/16.
 */
public class DateActivityPresenter {
    private Context context;
    private DateActivityView view;
    private IDateActivityModel model;

    public DateActivityPresenter(Context context, DateActivityView view) {
        this.context = context;
        this.view = view;
        model = new DateActivityModel();
    }

    public void tv_date() {
        final ProgressDialog dialog = ProgressDialogUtils.from(context)
                .setTitle("提示:")
                .setMessage("正在预约中...")
                .setCanceledOnTouchOutside(false)
                .show();
        model.date(context, view.getName(), view.getTel(), view.getTime(), view.getPosition(), view.getAmount(), view.getGasTel(), view.getGasLocation(), view.getGasName(), new BmobListener() {
            @Override
            public void onSuccess() {
                dialog.dismiss();
                String json = JsonUtils.getInstance()
                        .setName(view.getName())
                        .setTel(view.getTel())
                        .setTime(view.getTime())
                        .setOilStyle(context.getResources().getStringArray(R.array.oilStyle)[view.getPosition()])
                        .setMoney(view.getAmount())
                        .setGasTel(view.getGasTel())
                        .setGasLocation(view.getGasLocation())
                        .setGasName(view.getGasName())
                        .complete();
                Toast.makeText(context, "预约成功", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, DateSuccessActivity.class).putExtra(Config.KEY_DATE_TIME, view.getTime()).putExtra(Config.KEY_GAS_NAME, view.getGasName()).putExtra(Config.KEY_DATE_JSON, json));
            }

            @Override
            public void onFailure(int i, String s) {
                dialog.dismiss();
                if (i==1){
                    return;
                }
                Toast.makeText(context, BmobError.error(i), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void tv_my_date() {
        context.startActivity(new Intent(context, MyDateActivity.class));
    }

}
