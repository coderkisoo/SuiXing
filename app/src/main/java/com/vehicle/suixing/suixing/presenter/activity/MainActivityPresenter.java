package com.vehicle.suixing.suixing.presenter.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.app.SuixingApp;
import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.callback.BmobListener;
import com.vehicle.suixing.suixing.callback.UpdateList;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.model.activity.IMainModel;
import com.vehicle.suixing.suixing.model.impl.activity.MainModel;
import com.vehicle.suixing.suixing.ui.activity.SplashActivity;
import com.vehicle.suixing.suixing.ui.fragment.main.AboutUsFragment;
import com.vehicle.suixing.suixing.ui.fragment.main.GasStationFragment;
import com.vehicle.suixing.suixing.ui.fragment.main.MeFragment;
import com.vehicle.suixing.suixing.ui.fragment.main.VehicleInformationFragment;
import com.vehicle.suixing.suixing.ui.fragment.peccany.PeccanyFragment;
import com.vehicle.suixing.suixing.util.Log;
import com.vehicle.suixing.suixing.util.RegisterUtils.BmobError;
import com.vehicle.suixing.suixing.util.RegisterUtils.SpUtils;
import com.vehicle.suixing.suixing.util.dataBase.DbDao;
import com.vehicle.suixing.suixing.view.activity.MainActivityView;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by KiSoo on 2016/4/4.
 */
public class MainActivityPresenter implements UpdateList {
    private String TAG = MainActivityPresenter.class.getName();
    private Context context;
    private MainActivityView mainActivityView;
    private FragmentManager fragmentManager;
    private List<Fragment> fragments;
    private IMainModel model;
    private Fragment nowFragment;
    private int CURRENT_TYPE = 100;
    private final int TYPE_ME = 0;
    private final int TYPE_VEHICLE = 1;
    private final int TYPE_ADD_GAS = 2;
    private final int TYPE_PECCANY = 3;
    private final int TYPE_ABOUT_US = 4;

    public MainActivityPresenter(final MainActivityView view, final Context context, FragmentManager fragmentManager) {
        this.mainActivityView = view;
        this.context = context;
        this.fragmentManager = fragmentManager;
        model = new MainModel();
    }

    /**
     * 利用本地存储好的user来更新信息
     * 更新用户信息，检查账号密码是否有误
     */

    public void init() {
        model.initUser(context, this, new BmobListener() {
            @Override
            public void onSuccess() {
//                fragments.get(1).notifyAll();
            }

            @Override
            public void onFailure(int i, String s) {
                logOut();
                mainActivityView.showToast(BmobError.error(i));
            }

        });
        /**
         * 初始化fragment列表
         * */
        fragments = new ArrayList<>();
        fragments.add(new MeFragment(mainActivityView));//我的
        fragments.add(new VehicleInformationFragment());//车辆信息
        fragments.add(new GasStationFragment());//加油站信息
        fragments.add(new PeccanyFragment());//违章查询
        fragments.add(new AboutUsFragment());//关于我们
        startFragment(TYPE_VEHICLE);
    }

    public void onResume() {
        User users = SpUtils.getUsers(context);
        (fragments.get(TYPE_VEHICLE)).onResume();
        mainActivityView.UpdateName(users.getName());
        mainActivityView.updateMotto(users.getMotto());
        mainActivityView.updateHead(users.getHead());
    }

    /***
     * 我的
     */
    public void me() {
        if (!SuixingApp.hasUser) {
            Toast.makeText(context, "请先登录...", Toast.LENGTH_SHORT).show();
            logOut();
        } else {
            Log.e(TAG, "已经有了用户");
            mainActivityView.closeDrawer();
            startFragment(TYPE_ME);
        }
    }

    /***
     * 车辆信息
     */
    public void vehicle() {
        startFragment(TYPE_VEHICLE);
        mainActivityView.closeDrawer();
    }

    /***
     * 加油
     */
    public void getGas() {
        if (!SuixingApp.hasUser) {
            Toast.makeText(context, "请先登录...", Toast.LENGTH_SHORT).show();
            logOut();
        } else {
            Log.d(TAG, "已经有了用户");
            mainActivityView.closeDrawer();
            startFragment(TYPE_ADD_GAS);
        }
    }

    /***
     * 违章查询
     */
    public void peccany() {
        startFragment(TYPE_PECCANY);
        mainActivityView.closeDrawer();
    }

    public void aboutUs() {
        startFragment(TYPE_ABOUT_US);
        mainActivityView.closeDrawer();
    }

    public void logOut() {
        Log.d(TAG, "退出");
        final MaterialDialog dialog = new MaterialDialog(context);
        dialog.setTitle("提示:")
                .setMessage("你确定要退出吗?")
                .setCanceledOnTouchOutside(true)
                .setPositiveButton("是的", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        SpUtils.clearUsers(context);
                        User.logOut(context);
                        context.startActivity(new Intent(context, SplashActivity.class).putExtra("start", 2));
                        DbDao.clearAll(context);
                        SuixingApp.hasUser = false;
                        Config.USERNAME = "";
                        mainActivityView.finish();
                    }
                })
                .setNegativeButton("我点错了", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }).show();

    }

    private void startFragment(int type) {
        if (CURRENT_TYPE == type)
            return;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!fragments.get(type).isAdded()) {
            transaction.add(R.id.fl_main, fragments.get(type));
        } else {
            transaction.show(fragments.get(type));
        }
        if (null != nowFragment) {
            transaction.hide(nowFragment);
        }
        nowFragment = fragments.get(type);
        Log.d(TAG, "现在所处的ftagment为:" + nowFragment);
        transaction.commit();
        CURRENT_TYPE = type;
    }


    @Override
    public void updateList(List<VehicleInformation> infos) {
        SuixingApp.infos = infos;
//        fragments.get(1).notifyAll();
        fragments.get(1).onResume();
        Toast.makeText(context,"车辆数据已更新",Toast.LENGTH_SHORT).show();
    }


}
