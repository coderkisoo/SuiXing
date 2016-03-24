package com.vehicle.suixing.suixing.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.VehicleInformation;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.ui.activity.AddVehicleActivity;
import com.vehicle.suixing.suixing.ui.adapter.MyPageTransFormer;
import com.vehicle.suixing.suixing.ui.adapter.MyPagerAdapter;
import com.vehicle.suixing.suixing.util.DbDao;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/3/20.
 */
public class VehicleInformationFragment extends Fragment {
    private String TAG = "VehicleInformationFragment";
    private View view;
    private List<VehicleInformation> info;
    private int[] drawable = {R.mipmap.vehicle_1, R.mipmap.vehicle_2, R.mipmap.vehicle_3, R.mipmap.vehicle_4};
    @Bind(R.id.vp_choose_vehicle_list)
    ViewPager vp_choose_vehicle_list;
    @Bind(R.id.ll_container)
    LinearLayout ll_container;
    /**
     * 车上的数据绑定
     * */
    @Bind(R.id.tv_vehicle_information_name)
    TextView tv_name;
    @Bind(R.id.tv_vehicle_information_number)
    TextView tv_num;
    @Bind(R.id.tv_vehicle_information_gas_percent)
    TextView tv_percent;
    @Bind(R.id.tv_vehicle_information_level)
    TextView tv_size;
    @Bind(R.id.tv_vehicle_information_mileage)
    TextView tv_mileage;
    @Bind(R.id.tv_vehicle_information_engine_version)
    TextView tv_model;
    @Bind(R.id.tv_vehicle_information_engine_function)
    TextView tv_function;
    @Bind(R.id.tv_vehicle_information_speed_changer)
    TextView tv_speed;
    @Bind(R.id.tv_vehicle_information_light)
    TextView tv_light;



    @OnClick(R.id.iv_add_vehicle)
    void iv_add_vehicle() {
        Log.d(TAG, "iv_add_vehicle: 添加汽车");
        /**
         * 启动相机是耗时操作，防止多次点击创建多个activity
         * */
        startActivity(new Intent(getActivity(), AddVehicleActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.vehicle_information, null);
        ButterKnife.bind(this, view);
//        final ViewPager vp_choose_vehicle_list = (ViewPager) view.findViewById(R.id.vp_choose_vehicle_list);
        ll_container.setOnTouchListener(new View.OnTouchListener() {
            /**
             * 将父控件的onTouch()方法分发
             * */
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return vp_choose_vehicle_list.dispatchTouchEvent(event);
            }
        });
        vp_choose_vehicle_list.setPageTransformer(true, new MyPageTransFormer());
        vp_choose_vehicle_list.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                initInfo(info.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        info = DbDao.queryPart(getActivity(), Config.userName);
        Log.e(TAG, info.size() + "");
        vp_choose_vehicle_list.setAdapter(new MyPagerAdapter(getActivity(), info));
        vp_choose_vehicle_list.setOffscreenPageLimit(info.size());
        if (info.size() > 0)
            initInfo(info.get(0));
    }


    private void initInfo(VehicleInformation vehicleInformation) {
        tv_name.setText(vehicleInformation.getName());
        tv_num.setText(vehicleInformation.getNum());
        tv_percent.setText(vehicleInformation.getPercent());
        tv_size.setText(vehicleInformation.getSize());
        tv_mileage.setText(vehicleInformation.getMileage());
        tv_model.setText(vehicleInformation.getModel());
        tv_function.setText(vehicleInformation.getFunction());
        tv_speed.setText(vehicleInformation.getSpeed());
        tv_light.setText(vehicleInformation.getLight());
    }
}
