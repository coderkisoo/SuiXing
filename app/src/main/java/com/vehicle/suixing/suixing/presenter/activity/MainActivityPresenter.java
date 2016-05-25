package com.vehicle.suixing.suixing.presenter.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.app.SuixingApp;
import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.callback.BmobListener;
import com.vehicle.suixing.suixing.callback.UpdateList;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.model.MusicPlayView;
import com.vehicle.suixing.suixing.model.activity.IMainModel;
import com.vehicle.suixing.suixing.model.impl.activity.MainModel;
import com.vehicle.suixing.suixing.ui.activity.SplashActivity;
import com.vehicle.suixing.suixing.ui.fragment.main.GasStationFragment;
import com.vehicle.suixing.suixing.ui.fragment.main.MeFragment;
import com.vehicle.suixing.suixing.ui.fragment.main.VehicleInformationFragment;
import com.vehicle.suixing.suixing.ui.fragment.peccany.PeccanyFragment;
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
    private Type type;
    private Fragment nowFragment;

    private enum Type {
        Me, vehicleInfo, peccany, gas, about_us
    }

    private MusicPlayView musicPlayView;

    public MainActivityPresenter(final MainActivityView view, final Context context, FragmentManager fragmentManager) {
        this.mainActivityView = view;
        this.context = context;
        this.fragmentManager = fragmentManager;
        model = new MainModel();
        musicPlayView = new MusicPlayView(context);
    }

    /**
     * 利用本地存储好的user来更新信息
     * 更新用户信息，检查账号密码是否有误
     */

    public void init() {
        model.initUser(context, this, new BmobListener() {
            @Override
            public void onSuccess() {
                fragments.get(1).notifyAll();
            }

            @Override
            public void onFailure(int i, String s) {
                logOut();
                mainActivityView.showToast(BmobError.error(i));
            }

        });
        initFragments();
    }
    //初始化floatactionbutton


    public void onResume() {
        User users = SpUtils.getUsers(context);
        mainActivityView.UpdateName(users.getName());
        mainActivityView.updateMotto(users.getMotto());
        mainActivityView.updateHead(users.getHead());
    }

    private void initFragments() {
        /**
         * 初始化fragment列表
         * */
        fragments = new ArrayList<>();
        fragments.add(new MeFragment(mainActivityView));
        fragments.add(new VehicleInformationFragment());
        fragments.add(new GasStationFragment());
        fragments.add(new PeccanyFragment());
        type = Type.vehicleInfo;
        startFragment(type);
    }

    private void startFragment(Type type) {

        switch (type) {
            case Me:
                beginWith(0);
                break;
            case vehicleInfo:
                beginWith(1);
                break;
            case gas:
                beginWith(2);
                break;
            case peccany:
                beginWith(3);
                break;
            case about_us:
                break;
        }

    }

    private void beginWith(int count) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!fragments.get(count).isAdded()) {
            transaction.add(R.id.fl_main, fragments.get(count));
        } else {
            transaction.show(fragments.get(count));
        }
        if (null != nowFragment) {
            transaction.hide(nowFragment);
        }
        nowFragment = fragments.get(count);
        Log.d(TAG,"现在所处的ftagment为:"+nowFragment);
        transaction.commit();
    }


    public void peccany() {
        if (type != Type.peccany) {
            type = Type.peccany;
            showFab();
            startFragment(type);
        }
        mainActivityView.closeDrawer();
    }

    public void aboutUs() {
        if (type != Type.about_us) {
            type = Type.about_us;
        }
        mainActivityView.closeDrawer();
    }

    public void logOut() {
        Log.d(TAG, "退出");
        dismissFab(false);
        final MaterialDialog dialog = new MaterialDialog(context);
        dialog.setTitle("提示:")
                .setMessage("你确定要退出吗?")
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

    public void me() {
        if (type != Type.Me) {
            type = Type.Me;
            dismissFab(false);
            if (!SuixingApp.hasUser) {
                Toast.makeText(context, "请先登录...", Toast.LENGTH_SHORT).show();
                logOut();
            } else {
                Log.e(TAG, "已经有了用户");
                mainActivityView.closeDrawer();
                startFragment(type);
            }
        }
    }

    public void vehicle() {
        showFab();
        if (type != Type.vehicleInfo) {
            type = Type.vehicleInfo;
            startFragment(type);
        }
        mainActivityView.closeDrawer();
    }

    public void getGas() {
        type = Type.gas;
        if (!SuixingApp.hasUser) {
            Toast.makeText(context, "请先登录...", Toast.LENGTH_SHORT).show();
            logOut();
        } else {
            Log.e(TAG, "已经有了用户");
            mainActivityView.closeDrawer();
            startFragment(type);
        }
    }

    @Override
    public void updateList(List<VehicleInformation> infos) {
        SuixingApp.infos = infos;
    }

    public void showFab() {
        if (musicPlayView.isShow())
            return;
        if (type != Type.Me) {
            musicPlayView.showFab();
            musicPlayView.bindService();
        }
    }

    public void dismissFab(boolean show) {
        if (!musicPlayView.isShow())
            return;
        musicPlayView.dismissFab(show);
        musicPlayView.unBindSerice();

    }


}
