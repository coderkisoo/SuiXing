package com.vehicle.suixing.suixing.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.app.SuixingApp;
import com.vehicle.suixing.suixing.presenter.activity.MainActivityPresenter;
import com.vehicle.suixing.suixing.ui.BaseActivity;
import com.vehicle.suixing.suixing.util.Log;
import com.vehicle.suixing.suixing.view.activity.MainActivityView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements MainActivityView {
    private String TAG = MainActivity.class.getName();
    private MainActivityPresenter presenter;
    private View parent;
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
    @Bind(R.id.ll_root_view)
    LinearLayout ll_root_view;

    @OnClick(R.id.iv_toolbar_left_image)
    void iv_toolbar_left_image() {
        dl_view.openDrawer(Gravity.LEFT);
    }

    @OnClick(R.id.ll_me)
    void ll_me() {
        presenter.me();
        initToolbar(toolbar, R.mipmap.iv_swipe_left, "我的", false);
    }

    @OnClick(R.id.ll_vehicle_information)
    void ll_vehicle_information() {
        /**
         * 车辆信息
         *
         * */
        presenter.vehicle();
        initToolbar(toolbar, R.mipmap.iv_swipe_left, "车辆信息", false);
    }

    @OnClick(R.id.ll_get_gas)
    void ll_get_gas() {
        /**
         * 加油
         * */
        presenter.getGas();
        initToolbar(toolbar, R.mipmap.iv_swipe_left, "加油", true);

    }

    @OnClick(R.id.iv_me)
    void iv_me() {
        startActivity(new Intent(this, MyDateActivity.class));
    }

    @OnClick(R.id.ll_peccany)
    void ll_peccany() {
        /**
         * 交通违法信息
         * */
        presenter.peccany();
        initToolbar(toolbar, R.mipmap.iv_swipe_left, "交通违法", false);
    }

    @OnClick(R.id.ll_about_us)
    void ll_about_us() {
        /**
         * 关于我们
         * */
        presenter.aboutUs();
        initToolbar(toolbar, R.mipmap.iv_swipe_left, "关于我们", false);

    }

    @OnClick(R.id.ll_back)
    void ll_back() {
        /**
         * 退出
         * */
        presenter.logOut();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SuixingApp.removeActivity(this);
        SuixingApp.clearAll();
        SuixingApp.addActivity(this);
        parent = View.inflate(this, R.layout.activity_main, null);
        setContentView(parent);
        ButterKnife.bind(this);
        initToolbar(toolbar, R.mipmap.iv_swipe_left, "车辆信息", false);
        presenter = new MainActivityPresenter(this, this, getSupportFragmentManager());
        presenter.init();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {//两次按键小于2秒时，退出应用
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    this.startActivity(intent);
                    return true;
                }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void UpdateName(String name) {
        dl_tv_name.setText(name);
    }


    @Override
    public void updateMotto(String motto) {
        tv_motto.setText(motto);
    }

    /**
     * 设置头像
     */
    @Override
    public void updateHead(String head) {
        ImageLoader.getInstance().displayImage(head, civ_head);
        Log.d(TAG, "成功设置头像" + head);
    }

    @Override
    public void closeDrawer() {
        dl_view.closeDrawer(Gravity.LEFT);
    }

    @Override
    public void resume() {
        presenter.onResume();
    }

    @Override
    public View getParentView() {
        return parent;
    }

    @Override
    public ViewGroup getRootViewGroup() {
        return ll_root_view;
    }

    @Override
    public void showToast(String toast) {
        toast(toast);
    }
}
