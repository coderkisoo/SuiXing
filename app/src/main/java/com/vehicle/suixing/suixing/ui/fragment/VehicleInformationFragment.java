package com.vehicle.suixing.suixing.ui.fragment;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.ui.activity.AddVehicleActivity;
import com.vehicle.suixing.suixing.ui.adapter.MyPageTransFormer;
import com.vehicle.suixing.suixing.ui.adapter.MyPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/3/20.
 */
public class VehicleInformationFragment extends Fragment {
    private View view;
    private String TAG = "VehicleInformationFragment";
    private int[] drawable = {R.mipmap.vehicle_1,R.mipmap.vehicle_2,R.mipmap.vehicle_3,R.mipmap.vehicle_4};
    @Bind(R.id.vp_choose_vehicle_list)
    ViewPager vp_choose_vehicle_list;
    @Bind(R.id.rl_container)
    RelativeLayout rl_container;

    @OnClick(R.id.iv_add_vehicle)
    void iv_add_vehicle() {
        Log.d(TAG, "iv_add_vehicle: 添加汽车");
        startActivity(new Intent(getActivity(), AddVehicleActivity.class));
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.vehicle_information, null);
        ButterKnife.bind(this, view);
//        final ViewPager vp_choose_vehicle_list = (ViewPager) view.findViewById(R.id.vp_choose_vehicle_list);
        vp_choose_vehicle_list.setOffscreenPageLimit(drawable.length);
        rl_container.setOnTouchListener(new View.OnTouchListener() {
            /**
             * 将父控件的onTouch()方法分发
             * */
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return vp_choose_vehicle_list.dispatchTouchEvent(event);
            }
        });
        vp_choose_vehicle_list.setAdapter(new MyPagerAdapter(getActivity(), drawable));
        vp_choose_vehicle_list.setPageTransformer(true, new MyPageTransFormer());
        return view;
    }


}
