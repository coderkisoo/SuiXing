package com.vehicle.suixing.suixing.ui.fragment.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.TextureMapView;
import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.presenter.fragment.GasStationFragmentPresenter;
import com.vehicle.suixing.suixing.view.fragment.GasStationFragmentView;

import butterknife.Bind;
import butterknife.ButterKnife;


public class GasStationFragment extends Fragment implements GasStationFragmentView, View.OnLongClickListener {

    @Bind(R.id.tmv_location)
    TextureMapView tmv_location;
    @Bind(R.id.tv_show_city)
    TextView tv_show_city;
    @Bind(R.id.ll_mapinfo)
    LinearLayout ll_mapinfo;

    private GasStationFragmentPresenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_addgass, container, false);
        ButterKnife.bind(this, parent);
        tmv_location.onCreate(savedInstanceState);
        tmv_location.setOnLongClickListener(this);
        presenter = new GasStationFragmentPresenter(this, getActivity());
        presenter.initMapView();
        return parent;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        tmv_location.onSaveInstanceState(outState);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        tmv_location.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        tmv_location.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        tmv_location.onPause();
    }

    @Override
    public AMap getMap() {
        return tmv_location.getMap();
    }

    @Override
    public void setNowLocation(String address) {
        tv_show_city.setText(address);
    }


    @Override
    public void showInfo() {
        ll_mapinfo.setVisibility(ll_mapinfo.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    @Override
    public View getParent() {
        return this.getView();
    }

    @Override
    public void disMissLL() {
        ll_mapinfo.setVisibility(View.GONE);
    }

    public void searchTarget() {
        presenter.searchTarget();
    }

    public GasStationFragment() {
    }

    @Override
    public boolean onLongClick(View v) {
        showInfo();
        return false;
    }
}