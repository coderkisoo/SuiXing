package com.vehicle.suixing.suixing.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.ui.BaseActivity;

/**
 * Created by KiSoo on 2016/7/21.
 */
public class WelcomeActivity extends BaseActivity {
    private String mSDCardPath = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (User.getCurrentUser(this, User.class) != null) {
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        }else {
            startActivity(new Intent(mContext,SplashActivity.class));
            finish();
        }

    }



}
