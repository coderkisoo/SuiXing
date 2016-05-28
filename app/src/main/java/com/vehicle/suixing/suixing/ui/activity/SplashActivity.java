package com.vehicle.suixing.suixing.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.ui.BaseActivity;
import com.vehicle.suixing.suixing.ui.fragment.splash.SplashFragment1;
import com.vehicle.suixing.suixing.ui.fragment.splash.SplashFragment2;
import com.vehicle.suixing.suixing.ui.fragment.splash.SplashFragment3;
import com.vehicle.suixing.suixing.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by KiSoo on 2016/3/29.
 */
public class SplashActivity extends BaseActivity {
    @Bind(R.id.vp_splash)
    ViewPager vp_splash;
    private String mSDCardPath = null;
    private String TAG = SplashActivity.class.getName();
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBaiduMap();
        if (User.getCurrentUser(this, User.class) != null) {
            /**
             * 已经登陆过了
             * */
            Log.e(TAG, "已经登录过了");
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
        initList();
        setContentView(R.layout.splash_layout);
        ButterKnife.bind(this);
        vp_splash.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        vp_splash.setCurrentItem(getIntent().getIntExtra("start", 0));
    }

    private void initBaiduMap() {
        if (initDirs()) {
            Log.d("导航引擎正在初始化");
            BaiduNaviManager.getInstance().init(
                    SplashActivity.this,
                    mSDCardPath,
                    Config.BAIDU_INIT,
                    new BaiduNaviManager.NaviInitListener() {
                        @Override
                        public void onAuthResult(final int status, final String msg) {
                            Log.d(status + msg);
                            if (0 == status)
                                return;
                            toast("key校验失败  " + msg);
                        }

                        @Override
                        public void initStart() {
                            //百度导航引擎初始化开始
                            Log.d("导航引擎正在初始化");
                            toast("导航引擎正在初始化...");
                        }

                        @Override
                        public void initSuccess() {
                            //百度导航引擎初始化成功
                            Log.d("导航引擎初始化成功");
                            BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_DAY);
                            BNaviSettingManager.setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
                            BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
                            BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);
                            BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
                        }

                        @Override
                        public void initFailed() {
                            //百度导航引擎初始化失败
                            Log.d("百度导航引擎初始化失败");
                            toast("百度导航引擎初始化失败");
                        }
                    },
                    null,
                    /**
                     * 内部TTS播报状态回传handler
                     */
                    new Handler() {
                        public void handleMessage(Message msg) {
                            int type = msg.what;
                            switch (type) {
                                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {

                                    break;
                                }
                                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {

                                    break;
                                }
                                default:
                                    break;
                            }
                        }
                    },
                    null);
        }

    }

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, Config.BAIDU_INIT);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }


    private void initList() {
        fragments = new ArrayList<>();
        fragments.add(new SplashFragment1());
        fragments.add(new SplashFragment2());
        fragments.add(new SplashFragment3());
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

}
