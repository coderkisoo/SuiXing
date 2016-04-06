package com.vehicle.suixing.suixing.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.model.PeccanydAcitivtyView;
import com.vehicle.suixing.suixing.presenter.PeccanydActivityPresenter;
import com.vehicle.suixing.suixing.ui.BaseSlidingActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/4/5.
 */
public class PeccanydActivity extends BaseSlidingActivity
        implements PeccanydAcitivtyView {
    private PeccanydActivityPresenter presenter;
    private FragmentManager fm;
    private int BLUE_COLOR ;
    private int WHITE_COLOR;
    private ProgressDialog dialog;
    @Bind(R.id.tv_past)
    TextView tv_past;
    @Bind(R.id.tv_now)
    TextView tv_now;
    @OnClick(R.id.iv_toolbar_left_image)
    void iv_toolbar_left_image(){
        finish();
    }

    @OnClick(R.id.tv_now)
    void tv_now() {
        presenter.now();
    }

    @OnClick(R.id.tv_past)
    void tv_past() {
        presenter.past();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_peccany);
        ButterKnife.bind(this);
        initInfo();
        VehicleInformation info = (VehicleInformation) getIntent().getSerializableExtra(Config.INFO);
        presenter = new PeccanydActivityPresenter(this, this, info);
    }

    private void initInfo() {
        BLUE_COLOR = ContextCompat.getColor(this, R.color.blue_color);
        WHITE_COLOR = ContextCompat.getColor(this, R.color.white_background);
        fm = getSupportFragmentManager();
    }

    @Override
    public void now(Fragment fragment) {
        tv_now.setTextColor(WHITE_COLOR);
        tv_now.setBackgroundResource(R.mipmap.used_1);
        tv_past.setTextColor(BLUE_COLOR);
        tv_past.setBackgroundResource(R.mipmap.unuse_1);
        fm.beginTransaction().replace(R.id.fl_peccany_list,fragment).commit();
    }

    @Override
    public void past(Fragment fragment) {
//        FragmentTransaction transaction = fm.beginTransaction();
//        if (!fragment.isAdded())
//            transaction.add(R.id.fl_peccany_list, fragment, "past");
        tv_now.setTextColor(BLUE_COLOR);
        tv_now.setBackgroundResource(R.mipmap.unused);
        tv_past.setTextColor(WHITE_COLOR);
        tv_past.setBackgroundResource(R.mipmap.used);
        fm.beginTransaction().replace(R.id.fl_peccany_list,fragment).commit();
    }

    @Override
    public void begin() {
        dialog = ProgressDialog.show(this, "提示", "正在查询中，请稍后...", false);
    }

    @Override
    public void dismiss() {
        dialog.dismiss();
    }

    @Override
    public void defau() {
        toast("网络状态不佳，请检查网络设置");
        dismiss();
    }
}
