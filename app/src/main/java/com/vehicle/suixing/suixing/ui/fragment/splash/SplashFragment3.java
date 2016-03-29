package com.vehicle.suixing.suixing.ui.fragment.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.ui.activity.LoginActivity;
import com.vehicle.suixing.suixing.ui.activity.RegisterActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/3/29.
 */
public class SplashFragment3 extends Fragment {
    @OnClick(R.id.tv_login)
    void login() {
        /**
         * 登录
         * */
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @OnClick(R.id.tv_register)
    void register() {
        startActivity(new Intent(getActivity(), RegisterActivity.class));
    }

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.splash3, null);
        ButterKnife.bind(this,view);
        return view;
    }
}
