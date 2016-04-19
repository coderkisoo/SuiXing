package com.vehicle.suixing.suixing.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.app.SuixingApp;
import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.callback.UpdateList;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.model.MainActivityView;
import com.vehicle.suixing.suixing.ui.activity.SplashActivity;
import com.vehicle.suixing.suixing.ui.fragment.main.MeFragment;
import com.vehicle.suixing.suixing.ui.fragment.main.VehicleInformationFragment;
import com.vehicle.suixing.suixing.ui.fragment.peccany.PeccanyFragment;
import com.vehicle.suixing.suixing.util.BmobError;
import com.vehicle.suixing.suixing.util.BmobUtils;
import com.vehicle.suixing.suixing.util.DbDao;
import com.vehicle.suixing.suixing.util.SaveUser;
import com.vehicle.suixing.suixing.util.UserSpUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by KiSoo on 2016/4/4.
 */
public class MainActivityPresenter implements UpdateList{
    private String TAG = MainActivityPresenter.class.getName();
    private Context context;
    private MainActivityView view;
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
            SuixingApp.hasUser = true;
            Log.e(TAG, "有缓存的用户");
            Config.USERNAME = user.getUsername();
            SuixingApp.infos = DbDao.queryPart(context,Config.USERNAME);
            BmobUtils.updateList(context,this);
            Log.v(TAG, "调用本地存好的");
            UserSpUtils.saveName(context,user.getName());
            UserSpUtils.saveMotto(context, user.getMotto());
            UserSpUtils.saveHead(context,user.getHead());

        } else {
            SuixingApp.hasUser = false;
        }
    }

    public void onResume(){
        User users = UserSpUtils.getUsers(context);
        view.UpdateName(users.getName());
        view.updateMotto(users.getMotto());
        view.updateHead(users.getHead());
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
        fragments.add(new MeFragment(view));
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
        UserSpUtils.clearUsers(context);
        User.logOut(context);
        context.startActivity(new Intent(context, SplashActivity.class));
        DbDao.clearAll(context);
        SuixingApp.hasUser = false;
        Config.USERNAME = "";
        view.finish();
    }

    public void me() {
        if (!SuixingApp.hasUser) {
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

    @Override
    public void updateList(List<VehicleInformation> infos) {
        SuixingApp.infos = infos;
    }
}
