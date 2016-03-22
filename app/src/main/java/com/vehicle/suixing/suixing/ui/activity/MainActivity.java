package com.vehicle.suixing.suixing.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.ui.BaseActivity;
import com.vehicle.suixing.suixing.ui.fragment.VehicleInformationFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity {
    @Bind(R.id.tb_main_toobar)
    Toolbar toolbar;
    @Bind(R.id.dl_main_drawer)
    DrawerLayout dl_view;
    @OnClick(R.id.iv_toolbar_left_image)
    void iv_toolbar_left_image(){
        dl_view.openDrawer(Gravity.LEFT);
    }
    @OnClick(R.id.ll_me)
    void ll_me(){

    }
    @OnClick(R.id.ll_vehicle_information)
    void ll_vehicle_information(){
        /**
         * 车辆信息
         * */
    }
    @OnClick(R.id.ll_get_gas)
    void ll_get_gas(){
        /**
         * 加油
         * */
    }
    @OnClick(R.id.ll_music)
    void ll_music(){
        /**
         * 音乐
         * */
    }
    @OnClick(R.id.ll_peccany)
    void  ll_peccany(){
        /**
         * 交通违法信息
         * */
    }
    @OnClick(R.id.ll_about_us)
    void ll_about_us(){
        /**
         * 关于我们
         * */
    }
    @OnClick(R.id.ll_back)
    void ll_back(){
        /**
         * 退出
         * */
    }
    @OnClick(R.id.ll_location)
    void ll_location(){
        /**
         * 定位
         * */
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, Config.bmobId);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar(toolbar, R.mipmap.iv_swipe_left, "车辆信息", true);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_main,new VehicleInformationFragment()).commit();
    }
}
