package com.vehicle.suixing.suixing.ui.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vehicle.suixing.suixing.R;

/**
 * Created by KiSoo on 2016/6/1.
 */
public class AboutUsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about_us,null);
    }

    public AboutUsFragment() {
    }
}
