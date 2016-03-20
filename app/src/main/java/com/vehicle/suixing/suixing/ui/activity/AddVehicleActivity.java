package com.vehicle.suixing.suixing.ui.activity;

import android.os.Bundle;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.ui.BaseSlidingActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/3/20.
 */
public class AddVehicleActivity extends BaseSlidingActivity {
    @OnClick(R.id.iv_back)
    void iv_toolbar_left_image(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        ButterKnife.bind(this);

    }
}
