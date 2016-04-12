package com.vehicle.suixing.suixing.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.app.SuiXingApplication;
import com.vehicle.suixing.suixing.model.MainActivityView;
import com.vehicle.suixing.suixing.presenter.MainActivityPresenter;
import com.vehicle.suixing.suixing.ui.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements MainActivityView{
    private String TAG = MainActivity.class.getName();
    private MainActivityPresenter presenter;
    @Bind(R.id.tb_main_toobar)
    Toolbar toolbar;
    @Bind(R.id.dl_main_drawer)
    DrawerLayout dl_view;
    @Bind(R.id.dl_tv_name)
    TextView dl_tv_name;
    @Bind(R.id.tv_motto)
    TextView tv_motto;
    @Bind(R.id.civ_head)
    CircleImageView civ_head;

    @OnClick(R.id.iv_toolbar_left_image)
    void iv_toolbar_left_image() {
        dl_view.openDrawer(Gravity.LEFT);
    }

    @OnClick(R.id.ll_me)
    void ll_me() {
        presenter.me();
        initToolbar(toolbar, R.mipmap.iv_swipe_left, "我的", true);
    }

    @OnClick(R.id.ll_vehicle_information)
    void ll_vehicle_information() {
        /**
         * 车辆信息
         *
         * */
        presenter.vehicle();
        initToolbar(toolbar, R.mipmap.iv_swipe_left, "车辆信息", true);
    }

    @OnClick(R.id.ll_get_gas)
    void ll_get_gas() {
        /**
         * 加油
         * */
        presenter.getGas();
        initToolbar(toolbar, R.mipmap.iv_swipe_left, "加油", true);

    }

    @OnClick(R.id.ll_music)
    void ll_music() {
        /**
         * 音乐
         * */
        presenter.music();
    }

    @OnClick(R.id.ll_peccany)
    void ll_peccany() {
        /**
         * 交通违法信息
         * */
        presenter.peccany();
        initToolbar(toolbar, R.mipmap.iv_swipe_left, "交通违法", true);
    }

    @OnClick(R.id.ll_about_us)
    void ll_about_us() {
        /**
         * 关于我们
         * */
        presenter.aboutUs();
    }

    @OnClick(R.id.ll_back)
    void ll_back() {
        /**
         * 退出
         * */
        presenter.logOut();
    }

    @OnClick(R.id.ll_location)
    void ll_location() {
        /**
         * 定位
         * */
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SuiXingApplication.removeActivity(this);
        SuiXingApplication.clearAll();
        SuiXingApplication.addActivity(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar(toolbar, R.mipmap.iv_swipe_left, "车辆信息", true);
        presenter = new MainActivityPresenter(this,this,getSupportFragmentManager());
        presenter.init();
    }


    @Override
    public void logOut() {
        finish();
    }

    @Override
    public void UpdateName(String name) {
        dl_tv_name.setText(name);
    }


    @Override
    public void updateMotto(String motto) {
        tv_motto.setText(motto);
    }

    @Override
    public void updateHead(String head) {
        ImageLoader.getInstance().displayImage(head, civ_head);
    }

    @Override
    public void closeDrawer() {
        dl_view.closeDrawer(Gravity.LEFT);
    }

    @Override
    public void showToast(String toast) {
        toast(toast);
    }




}
