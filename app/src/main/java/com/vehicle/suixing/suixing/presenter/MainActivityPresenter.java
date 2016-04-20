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
import com.vehicle.suixing.suixing.callback.BmobListener;
import com.vehicle.suixing.suixing.callback.UpdateList;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.model.activity.IMainActivityModel;
import com.vehicle.suixing.suixing.model.impl.activity.MainActivityModel;
import com.vehicle.suixing.suixing.ui.activity.SplashActivity;
import com.vehicle.suixing.suixing.ui.fragment.main.MeFragment;
import com.vehicle.suixing.suixing.ui.fragment.main.VehicleInformationFragment;
import com.vehicle.suixing.suixing.ui.fragment.peccany.PeccanyFragment;
import com.vehicle.suixing.suixing.util.BmobError;
import com.vehicle.suixing.suixing.util.DbDao;
import com.vehicle.suixing.suixing.util.UserSpUtils;
import com.vehicle.suixing.suixing.view.activity.MainActivityView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KiSoo on 2016/4/4.
 */
public class MainActivityPresenter implements UpdateList{
    private String TAG = MainActivityPresenter.class.getName();
    private Context context;
    private MainActivityView view;
    private FragmentManager fragmentManager;
    private List<Fragment> fragments;
    private IMainActivityModel model;
    public MainActivityPresenter(MainActivityView view, Context context, FragmentManager fragmentManager) {
        this.view = view;
        this.context = context;
        this.fragmentManager = fragmentManager;
        model = new MainActivityModel();
    }

    /**
     * 利用本地存储好的user来更新信息
     * 更新用户信息，检查账号密码是否有误
     */

    public void init() {
        model.initUser(context, this, new BmobListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {
                logOut();
                view.showToast(BmobError.error(i));

            }

            @Override
            public void onSuccess(List list) {

            }
        });
        initFragments();
    }

    public void onResume(){
        User users = UserSpUtils.getUsers(context);
        view.UpdateName(users.getName());
        view.updateMotto(users.getMotto());
        view.updateHead(users.getHead());
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
        context.startActivity(new Intent(context, SplashActivity.class).putExtra("start",2));
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
