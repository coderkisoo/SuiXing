package com.vehicle.suixing.suixing.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.app.SuiXingApplication;
import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.bean.BmobBean.Users;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.model.MainActivityView;
import com.vehicle.suixing.suixing.ui.activity.SplashActivity;
import com.vehicle.suixing.suixing.ui.fragment.main.MeFragment;
import com.vehicle.suixing.suixing.ui.fragment.main.VehicleInformationFragment;
import com.vehicle.suixing.suixing.ui.fragment.peccany.PeccanyFragment;
import com.vehicle.suixing.suixing.util.BmobError;
import com.vehicle.suixing.suixing.util.SaveUser;
import com.vehicle.suixing.suixing.util.UserSpUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by KiSoo on 2016/4/4.
 */
public class MainActivityPresenter {
    private String TAG = MainActivityPresenter.class.getName();
    private Context context;
    private MainActivityView view;
    private SharedPreferences sp;
    private FragmentManager fragmentManager;
    private List<Fragment> fragments;

    public MainActivityPresenter(MainActivityView view, Context context, FragmentManager fragmentManager) {
        this.view = view;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    /**
     * 利用本地存储好的user来更新信息
     */
    public void init() {
        initUser();
        initFragments();
    }

    private void initUser() {
        User user = BmobUser.getCurrentUser(context, User.class);
        if (user != null) {
            /**
             * 更新信息
             * */
            updateUserInfo(user);
            SuiXingApplication.hasUser = true;
            Log.e(TAG, "有缓存的用户");
            Config.USERNAME = user.getUsername();
            Log.v(TAG, "调用本地存好的");
            sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("name", user.getName());
            editor.putString("motto", user.getMotto());
            if (null != user.getHead())
                editor.putString("head", user.getHead().getFileUrl(context));
            editor.apply();
            Users users = UserSpUtils.getUsers(context);
            view.UpdateName(users.getName());
            view.updateMotto(users.getMotto());
            view.updateHead(users.getHead());
        } else {
            SuiXingApplication.hasUser = false;
        }
    }


    /**
     * 更新用户信息，检查账号密码是否有误
     */


    private void updateUserInfo(User newUser) {
        List<String> userInfo = SaveUser.getUser(context);
        newUser.setUsername(userInfo.get(0));
        newUser.setPassword(userInfo.get(1));
        newUser.login(context, new SaveListener() {
            @Override
            public void onSuccess() {
                /**
                 * 登录成功，更新好了
                 * */
            }

            @Override
            public void onFailure(int i, String s) {
                /**
                 * 登录失败，返回登录界面
                 * */
                if (i == 101) {
                    /**
                     * 退出登录
                     * */
                    view.showToast(BmobError.error(i));
                    view.logOut();
                }
            }
        });
    }

    private void initFragments() {
        /**
         * 初始化fragment列表
         * */
        fragments = new ArrayList<>();
        fragments.add(new MeFragment());
        fragments.add(new VehicleInformationFragment());
        fragments.add(new PeccanyFragment());
        startFragment(fragments.get(1));

    }

    private void startFragment(Fragment newFragment) {
        fragmentManager.beginTransaction().replace(R.id.fl_main, newFragment).commit();
    }


    public void peccany() {
        startFragment(fragments.get(2));
        view.closeDrawer();
    }

    public void aboutUs() {
        view.closeDrawer();
    }

    public void logOut() {
        User.logOut(context);
        context.startActivity(new Intent(context, SplashActivity.class));
        SuiXingApplication.hasUser = false;
        Config.USERNAME = "";
        view.finish();
    }

    public void me() {
        if (SuiXingApplication.hasUser == false) {
            Toast.makeText(context,"请先登录...",Toast.LENGTH_SHORT).show();
            logOut();
        } else {
            Log.e(TAG, "已经有了用户");
            view.closeDrawer();
            startFragment(fragments.get(0));
        }
    }

    public void vehicle() {
        startFragment(fragments.get(1));
        view.closeDrawer();
    }

    public void getGas() {
        view.closeDrawer();
    }

    public void music() {
        view.closeDrawer();
    }
}
