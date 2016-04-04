package com.vehicle.suixing.suixing.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.ui.BaseActivity;
import com.vehicle.suixing.suixing.ui.fragment.splash.SplashFragment1;
import com.vehicle.suixing.suixing.ui.fragment.splash.SplashFragment2;
import com.vehicle.suixing.suixing.ui.fragment.splash.SplashFragment3;

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
    private String TAG = SplashActivity.class.getName();
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User user = User.getCurrentUser(this,User.class);
        if (user!=null){
            /**
             * 已经登陆过了
             * */
            Log.e(TAG,"已经登录过了");
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
    }


    private void initList() {
        fragments = new ArrayList<>();
        fragments.add(new SplashFragment1());
        fragments.add(new SplashFragment2());
        fragments.add(new SplashFragment3());
    }


}
