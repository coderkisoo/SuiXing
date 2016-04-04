package com.vehicle.suixing.suixing.presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.vehicle.suixing.suixing.app.SuiXingApplication;
import com.vehicle.suixing.suixing.model.VehicleInfoFragmentView;
import com.vehicle.suixing.suixing.ui.activity.AddVehicleActivity;
import com.vehicle.suixing.suixing.ui.activity.SplashActivity;

/**
 * Created by KiSoo on 2016/4/4.
 */
public class VehicleInfoFragmentPresenter {
    private VehicleInfoFragmentView view;
    private Context context;

    public VehicleInfoFragmentPresenter(VehicleInfoFragmentView view, Context context) {
        this.view = view;
        this.context = context;
    }
    public void addVehicle(){
        if (SuiXingApplication.hasUser == true){
            context.startActivity(new Intent(context, AddVehicleActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        }else {
            /**
             * 没有登录
             * */
            showToast("您当前没有登录账号，请先登录");
            context.startActivity(new Intent(context, SplashActivity.class));
        }
    }
    private void showToast(String toast){
        Toast.makeText(context,toast,Toast.LENGTH_SHORT).show();
    }
}