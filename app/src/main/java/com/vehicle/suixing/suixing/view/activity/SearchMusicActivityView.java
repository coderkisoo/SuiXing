package com.vehicle.suixing.suixing.view.activity;

import android.widget.AdapterView;

import com.vehicle.suixing.suixing.ui.adapter.MusicListAdapter;

/**
 * Created by KiSoo on 2016/5/13.
 */
public interface SearchMusicActivityView {
    String getSearchInfo();

    void setAdapter(MusicListAdapter adapter);

    void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener);
}
