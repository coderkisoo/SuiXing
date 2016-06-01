package com.vehicle.suixing.suixing.presenter.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.app.SuixingApp;
import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.ui.activity.AddVehicleActivity;
import com.vehicle.suixing.suixing.ui.activity.SplashActivity;
import com.vehicle.suixing.suixing.ui.adapter.MyPagerAdapter;
import com.vehicle.suixing.suixing.ui.fragment.main.VehicleInfoFragment;
import com.vehicle.suixing.suixing.util.Log;
import com.vehicle.suixing.suixing.view.fragment.VehicleInfoFragmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KiSoo on 2016/4/4.
 */
public class VehicleInfoFragmentPresenter {
    private static final String TAG = "VehicleInfoFragmentPresenter";
    private VehicleInfoFragmentView view;
    private Context context;
    private List<VehicleInfoFragment> fragments;
    private MyPagerAdapter adapter;
    private FragmentManager fragmentManager;
    private int lastPostion = 1000;
    public VehicleInfoFragmentPresenter(VehicleInfoFragmentView view, Context context, FragmentManager childFragmentManager) {
        this.view = view;
        this.context = context;
        this.fragmentManager = childFragmentManager;
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
        adapter = new MyPagerAdapter(context);
        view.setAdapter(adapter);
        if (SuixingApp.infos.size() > 0){
            startFragment(0);
        }
    }

    public void startFragment(int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!fragments.get(position).isAdded()) {
            transaction.add(R.id.fl_vehicle, fragments.get(position));
        } else {
            transaction.show(fragments.get(position));
        }
        if (1000 != lastPostion) {
            transaction.hide(fragments.get(lastPostion));
        }
        lastPostion = position;
        Log.d(TAG, "现在所处的ftagment为:" + position+"上一次："+lastPostion);
        transaction.commit();
    }

    public void initInfo() {
        fragments = new ArrayList<>();
        for (VehicleInformation info:SuixingApp.infos){
            fragments.add(new VehicleInfoFragment(info));
        }
    }
}
