package com.vehicle.suixing.suixing.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.app.SuiXingApplication;
import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.ui.BaseActivity;
import com.vehicle.suixing.suixing.ui.fragment.main.VehicleInformationFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {
    private String TAG = MainActivity.class.getName();
    private SharedPreferences sp;
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

    }

    @OnClick(R.id.ll_vehicle_information)
    void ll_vehicle_information() {
        /**
         * 车辆信息
         * */
    }

    @OnClick(R.id.ll_get_gas)
    void ll_get_gas() {
        /**
         * 加油
         * */
    }

    @OnClick(R.id.ll_music)
    void ll_music() {
        /**
         * 音乐
         * */
    }

    @OnClick(R.id.ll_peccany)
    void ll_peccany() {
        /**
         * 交通违法信息
         * */
    }

    @OnClick(R.id.ll_about_us)
    void ll_about_us() {
        /**
         * 关于我们
         * */
    }

    @OnClick(R.id.ll_back)
    void ll_back() {
        /**
         * 退出
         * */
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
        initUser();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_main, new VehicleInformationFragment()).commit();
    }
    /**
     * 利用本地存储好的user来更新信息
     * */
    private void initUser() {

        sp = getSharedPreferences("user", MODE_PRIVATE);
        User user = BmobUser.getCurrentUser(this, User.class);
        if (user != null) {
            SuiXingApplication.hasUser = true;
            Log.v(TAG, "调用本地存好的");
            ImageLoader.getInstance().displayImage(user.getHead().getFileUrl(this), civ_head);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("name", user.getName());
            editor.putString("motto", user.getMotto());
            editor.putString("username", user.getUsername());
            editor.putString("head", user.getHead().getFileUrl(this));
            editor.apply();
        }
        String name = sp.getString("name", "未登录");
        String motto = sp.getString("motto","暂无");
        String head = sp.getString("head","");
        dl_tv_name.setText(name);
        tv_motto.setText(motto);
        ImageLoader.getInstance().displayImage(head,civ_head);
    }

}
