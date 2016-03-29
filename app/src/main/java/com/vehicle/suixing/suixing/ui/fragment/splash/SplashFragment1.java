package com.vehicle.suixing.suixing.ui.fragment.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vehicle.suixing.suixing.R;

/**
 * Created by KiSoo on 2016/3/29.
 */
public class SplashFragment1 extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.splash1,null);
        return view;

    }
}
