package com.vehicle.suixing.suixing.model;

/**
 * Created by KiSoo on 2016/4/4.
 */
public interface MainActivityView extends BaseView{
    void logOut();

    void UpdateName(String name);

    void updateMotto(String motto);

    void updateHead(String head);

    void closeDrawer();

    void finish();

    void resume();
}
