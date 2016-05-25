package com.vehicle.suixing.suixing.presenter.activity;

import android.content.Context;

import com.vehicle.suixing.suixing.view.activity.PlayingActivityView;

/**
 * Created by KiSoo on 2016/5/13.
 */
public class PlayingPresenter {
    private Context context;
    private PlayingActivityView view;

    public PlayingPresenter(Context context, PlayingActivityView view) {
        this.context = context;
        this.view = view;
    }
}
