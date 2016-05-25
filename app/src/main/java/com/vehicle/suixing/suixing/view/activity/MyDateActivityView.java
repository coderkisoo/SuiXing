package com.vehicle.suixing.suixing.view.activity;

import android.widget.AdapterView;

import com.vehicle.suixing.suixing.ui.adapter.DateAdapter;

/**
 * Created by KiSoo on 2016/5/16.
 */
public interface MyDateActivityView {
    public void showRefresh(boolean refresh);

    void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener);

    void setAdapter(DateAdapter adapter);
}
