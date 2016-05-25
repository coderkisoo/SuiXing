package com.vehicle.suixing.suixing.ui.fragment.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.TextureMapView;
import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.presenter.fragment.GasStationFragmentPresenter;
import com.vehicle.suixing.suixing.view.fragment.GasStationFragmentView;

import butterknife.Bind;
import butterknife.ButterKnife;


public class GasStationFragment extends Fragment implements GasStationFragmentView{

    @Bind(R.id.tmv_location)
    TextureMapView tmv_location;
    @Bind(R.id.tv_show_city)
    TextView tv_show_city;
    private View parent;
    private GasStationFragmentPresenter presenter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_addgass, container, false);
        ButterKnife.bind(this,parent);
        presenter = new GasStationFragmentPresenter(this,getActivity());
        int count = tmv_location.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = tmv_location.getChildAt(i);
            // 隐藏百度logo ZoomControl
            if (child instanceof ZoomControls) {
                child.setVisibility(View.INVISIBLE);
            }
        }
        presenter.initLocation();
        return parent;
    }

    @Override
    public BaiduMap getMap() {
        return tmv_location.getMap();
    }

    @Override
    public void setNowLocation(String address) {
        tv_show_city.setText(address);
    }

    @Override
    public View getParent() {
        return parent;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        tmv_location.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        tmv_location.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        tmv_location.onPause();
    }
}