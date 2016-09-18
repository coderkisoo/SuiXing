package com.vehicle.suixing.suixing.presenter.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.vehicle.suixing.suixing.bean.BmobBean.Bmob_date_gasstation;
import com.vehicle.suixing.suixing.bean.MapBean.MapInfo;
import com.vehicle.suixing.suixing.callback.BmobListenerWithList;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.model.activity.IMyDateActivityModel;
import com.vehicle.suixing.suixing.model.impl.activity.MyDateActivityModel;
import com.vehicle.suixing.suixing.ui.activity.DateSuccessActivity;
import com.vehicle.suixing.suixing.ui.adapter.DateAdapter;
import com.vehicle.suixing.suixing.util.RegisterUtils.BmobError;
import com.vehicle.suixing.suixing.util.formatUtils.JsonUtils;
import com.vehicle.suixing.suixing.view.activity.MyDateActivityView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KiSoo on 2016/5/16.
 */
public class MyDateActivityPresenter {
    private Context context;
    private DateAdapter adapter;
    private MyDateActivityView view;
    private IMyDateActivityModel model;
    private List<Bmob_date_gasstation> dateList;
    private MapInfo startPoint,endPoint;
    public MyDateActivityPresenter(final Context context, MyDateActivityView view, Intent intent) {
        this.context = context;
        this.view = view;
        this.startPoint = intent.getParcelableExtra(Config.KEY_START_LATING);
        this.endPoint = intent.getParcelableExtra(Config.KEY_END_LATING);
        model = new MyDateActivityModel();
        dateList = new ArrayList<>();
        adapter = new DateAdapter(dateList,context);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bmob_date_gasstation info = dateList.get(position);
                String json = JsonUtils.getInstance()
                        .setName(info.getApp_username())
                        .setTel(info.getDate_uerPhonNum())
                        .setTime(info.getDate_time())
                        .setOilStyle(info.getDate_oilStyle())
                        .setMoney(info.getDate_money())
                        .setGasTel(info.getGasstation_phoneNum())
                        .setGasLocation(info.getGasstation_drass())
                        .setGasName(info.getGasstation_name())
                        .complete();
                context.startActivity(new Intent(context, DateSuccessActivity.class)
                        .putExtra(Config.KEY_DATE_TIME, info.getDate_time())
                        .putExtra(Config.KEY_START_LATING,startPoint)
                        .putExtra(Config.KEY_END_LATING,endPoint)
                        .putExtra(Config.KEY_GAS_NAME, info.getGasstation_name())
                        .putExtra(Config.KEY_DATE_JSON, json));
            }
        });
    }

    public void requestList() {
        view.showRefresh(true);
        model.requestList(context, new BmobListenerWithList<Bmob_date_gasstation>() {
            @Override
            public void onSuccess(List<Bmob_date_gasstation> list) {
                view.showRefresh(false);
                dateList = list;
                adapter.setDateList(dateList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onSuccess() {
                view.showRefresh(false);
            }

            @Override
            public void onFailure(int i, String s) {
                view.showRefresh(false);
                Toast.makeText(context, BmobError.error(i),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
