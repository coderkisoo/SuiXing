package com.vehicle.suixing.suixing.model.activity;

import android.content.Context;

import com.vehicle.suixing.suixing.callback.SearchInfo;

/**
 * Created by KiSoo on 2016/5/13.
 */
public interface ISearchMusicModel {

    void search(Context context,String searchInfo,SearchInfo callback);

    void getPath(Context context, int position, SearchInfo searchInfo);
}
