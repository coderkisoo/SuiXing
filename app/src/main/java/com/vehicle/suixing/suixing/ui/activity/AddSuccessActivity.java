package com.vehicle.suixing.suixing.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.view.activity.AddSuccessActivityView;
import com.vehicle.suixing.suixing.presenter.activity.AddSuccessActivityPresenter;
import com.vehicle.suixing.suixing.ui.BaseSlidingActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/3/21.
 */
public class AddSuccessActivity extends BaseSlidingActivity implements AddSuccessActivityView {

    private AddSuccessActivityPresenter presenter;

    @Bind(R.id.rl_islaunch)
    RelativeLayout rl_islaunch;
    @Bind(R.id.iv_vehicle_img)
    ImageView iv_vehicle_img;
    @Bind(R.id.pb_islaunch)
    ProgressBar pb_islaunch;
    @Bind(R.id.tv_vehicle_information_name)
    TextView tv_vehicle_information_name;
    @Bind(R.id.tv_vehicle_information_number)
    TextView tv_vehicle_information_number;
    @Bind(R.id.tv_add_success)
    TextView tv_add_success;

    @OnClick(R.id.tv_add_success)
    void tv_add_success() {
        presenter.addSuccess();
    }

    @OnClick(R.id.iv_toolbar_left_image)
    void iv_toolbar_left_image() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_success);
        ButterKnife.bind(this);
        presenter = new AddSuccessActivityPresenter(this, this);
        presenter.startDownLoad();
    }


    @Override
    public VehicleInformation getInformation() {
        return (VehicleInformation) getIntent().getSerializableExtra("INFO");
    }

    @Override
    public void launched() {
        rl_islaunch.setVisibility(View.GONE);
    }

    @Override
    public void launch() {
        rl_islaunch.setVisibility(View.VISIBLE);
        tv_add_success.setVisibility(View.GONE);
    }

    @Override
    public void tvAddSuccess() {
        tv_add_success.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToast(String toast) {
        toast(toast);
    }

    @Override
    public void displayImg(String url) {
        ImageLoader
                .getInstance()
                .displayImage(url, iv_vehicle_img);
    }

    @Override
    public void setName(String name) {
        tv_vehicle_information_name.setText(name);
    }

    @Override
    public void setNumber(String number) {
        tv_vehicle_information_number.setText(number);
    }

    @Override
    public void launchFailed() {
        pb_islaunch.setVisibility(View.GONE);
        finish();
    }

}
