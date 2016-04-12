package com.vehicle.suixing.suixing.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by KiSoo on 2016/4/11.
 */
public class InfoService extends IntentService {


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public InfoService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
