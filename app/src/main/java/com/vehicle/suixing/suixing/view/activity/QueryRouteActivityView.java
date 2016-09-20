package com.vehicle.suixing.suixing.view.activity;

import android.app.Activity;
import android.text.TextWatcher;

import com.vehicle.suixing.suixing.ui.adapter.RecentRouteAdapter;

/**
 * Created by KiSoo on 2016/7/19.
 */
public interface QueryRouteActivityView {
    void setStartWatcher(TextWatcher startWatcher);

    void setEndWatcher(TextWatcher endWatcher);

    void setAdapter(RecentRouteAdapter adapter);

    void setText(boolean isStart, String name);

    Activity getActivity();

    String getStart();

    String getEnd();
}
