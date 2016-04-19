package com.vehicle.suixing.suixing.view.activity;

import com.vehicle.suixing.suixing.view.BaseView;

/**
 * Created by KiSoo on 2016/4/4.
 */
public interface MainActivityView extends BaseView {


    void UpdateName(String name);

    void updateMotto(String motto);

    void updateHead(String head);

    void closeDrawer();

    void finish();

    void resume();
}
