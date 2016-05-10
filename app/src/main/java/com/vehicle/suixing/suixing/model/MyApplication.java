package com.vehicle.suixing.suixing.model;

import android.app.Application;

import com.baidu.apistore.sdk.ApiStoreSDK;

/**
 * Created by lenovo on 2016/3/31.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        // TODO 您的其他初始化流程
        ApiStoreSDK.init(this, "c18a63c08d9b4cc02a2afdf41ccbb606");
        super.onCreate();
    }
}