package com.vehicle.suixing.suixing.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.common.Config;

/**
 * Created by KiSoo on 2016/5/11.
 */
public class VehicleInfoReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setTicker("你有一条新的通知!")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("您有一条新的消息")
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX);

        switch (intent.getAction()) {
            case Config.ACTION_PERCENT_LESS:
                builder.setContentText("您的汽车当前油量不足20%，请加油后继续行驶。");
                break;
            case Config.ACTION_VEHICLE_ERROR:
                builder.setContentText("您的汽车有故障，请妥善修理后继续行驶。");
                break;
            case Config.ACTION_MILEAGE_MUCH:
                builder.setContentText("您的汽车当前行驶公里超过" + intent.getIntExtra(Config.KEY_MAX_MILEAGE, 15000) + "km，请维护后继续行驶。");
                break;
        }
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(Config.NOTIFATION_ID, builder.build());
        Config.NOTIFATION_ID++;
    }
}
