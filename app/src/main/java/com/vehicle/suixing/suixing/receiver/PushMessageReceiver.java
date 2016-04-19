package com.vehicle.suixing.suixing.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cn.bmob.push.PushConstants;

/**
 * Created by KiSoo on 2016/4/11.
 */
public class PushMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {

            Log.e("bmob", "客户端收到推送内容：" + intent.getStringExtra("msg"));

        }
    }

}
