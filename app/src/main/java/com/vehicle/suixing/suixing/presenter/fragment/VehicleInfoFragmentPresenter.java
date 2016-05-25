package com.vehicle.suixing.suixing.presenter.fragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.vehicle.suixing.suixing.app.SuixingApp;
import com.vehicle.suixing.suixing.ui.activity.AddVehicleActivity;
import com.vehicle.suixing.suixing.ui.activity.SplashActivity;
import com.vehicle.suixing.suixing.ui.adapter.MyPagerAdapter;
import com.vehicle.suixing.suixing.view.fragment.VehicleInfoFragmentView;

/**
 * Created by KiSoo on 2016/4/4.
 */
public class VehicleInfoFragmentPresenter {
    private static final String TAG = "VehicleInfoFragmentPresenter";
    private VehicleInfoFragmentView view;
    private Context context;
    private MyPagerAdapter adapter;
    public VehicleInfoFragmentPresenter(VehicleInfoFragmentView view, Context context) {
        this.view = view;
        this.context = context;
    }
    public void addVehicle(){
        if (SuixingApp.hasUser){
            context.startActivity(new Intent(context, AddVehicleActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        }else {
            /**
             * 没有登录
             * */
            showToast("您当前没有登录账号，请先登录");
            context.startActivity(new Intent(context, SplashActivity.class).putExtra("start",2));
        }
    }
    private void showToast(String toast){
        Toast.makeText(context,toast,Toast.LENGTH_SHORT).show();
    }

    public void setAdapter(){
        adapter = new MyPagerAdapter(context, SuixingApp.infos);
        view.setAdapter(adapter);
        if (SuixingApp.infos.size() > 0)
            view.initInfo(SuixingApp.infos.get(0));
    }

    public void update() {
        Log.d(TAG,"数据已经刷新");
        adapter.notifyDataSetChanged();
    }
}
