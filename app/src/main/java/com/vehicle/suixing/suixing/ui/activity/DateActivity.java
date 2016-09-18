package com.vehicle.suixing.suixing.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.presenter.activity.DateActivityPresenter;
import com.vehicle.suixing.suixing.ui.BaseSlidingActivity;
import com.vehicle.suixing.suixing.view.activity.DateActivityView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/5/16.
 */
public class DateActivity extends BaseSlidingActivity implements DateActivityView {
    @Bind(R.id.et_name)
    EditText et_name;
    @Bind(R.id.et_tel)
    EditText et_tel;
    @Bind(R.id.et_time)
    EditText et_time;
    @Bind(R.id.sp_oilStyle)
    Spinner sp_oilStyle;
    @Bind(R.id.et_money)
    EditText et_oilAmount;
    private DateActivityPresenter presenter;

    @OnClick(R.id.iv_toolbar_left_image)
    void iv_toolbar_left_image() {
        finish();
    }

    @OnClick(R.id.tv_date)
    void tv_date() {
        presenter.tv_date();
    }

    @OnClick(R.id.tv_my_date)
    void tv_my_date() {
        presenter.tv_my_date();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        ButterKnife.bind(this);
        presenter = new DateActivityPresenter(this, this,getIntent());
    }

    @Override
    public int getPosition() {
        return sp_oilStyle.getSelectedItemPosition();
    }

    @Override
    public String getName() {
        return et_name.getText().toString();
    }

    @Override
    public String getTel() {
        return et_tel.getText().toString();
    }

    @Override
    public String getAmount() {
        return et_oilAmount.getText().toString();
    }

    @Override
    public String getTime() {
        return et_time.getText().toString();
    }

    @Override
    public String getGasTel() {
        return getIntent().getStringExtra(Config.KEY_GAS_TEL);
    }

    @Override
    public String getGasLocation() {
        return getIntent().getStringExtra(Config.KEY_GAS_LOCATION);
    }

    @Override
    public String getGasName() {
        return getIntent().getStringExtra(Config.KEY_GAS_NAME);
    }
}
