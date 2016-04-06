package com.vehicle.suixing.suixing.model;

/**
 * Created by KiSoo on 2016/4/4.
 */
public interface RegisterActivityView {
    String getUsername();
    String getPassword1();
    String getPassword2();
    String getAuthCode();
    String getTel();
    void setClickable(Boolean clickable);
    void sending(int seconds);
    void sendable();

}
