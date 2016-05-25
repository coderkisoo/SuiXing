package com.vehicle.suixing.suixing.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.common.Config;

import org.json.JSONObject;

import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.ValueEventListener;

/**
 * Created by KiSoo on 2016/5/15.
 */
public class VehicleService extends Service {
    private Context context;
    private BmobRealTimeData VehicleErrorNotice = new BmobRealTimeData();
    private String TAG = "VehicleService";

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Log.d(TAG, "service启动");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //判断是否变化，如果变化 则请求网络
        VehicleErrorNotice.start(context, new ValueEventListener() {
            @Override
            public void onDataChange(JSONObject arg0) {
                Log.d(TAG, "检测到通知变化");
                if (BmobRealTimeData.ACTION_UPDATETABLE.equals(arg0.optString("action"))) {
                    JSONObject data = arg0.optJSONObject("data");
                    if (!data.optString("userName").equals(Config.USERNAME))
                        return;
                    //当服务器端的数据库里记录的汽油量少于20%时，给手机发送通知告诉汽车车主该去加油
                    if (20 > Integer.valueOf(data.optString("percent").substring(0, data.optString("percent").length() - 1)))
                        sendBroadcast(new Intent().setAction(Config.ACTION_PERCENT_LESS));
                    //当服务器端的数据库里记录的发动机出现异常、变速器出现异常或车灯有坏的时候，给手机发送通知告诉汽车车主需要进行维修
                    if (!(data.optString("function").equals("良好") && data.optString("speed").equals("正常") && data.optString("light").equals("好")))
                        sendBroadcast(new Intent().setAction(Config.ACTION_VEHICLE_ERROR));
                    //当服务器端的数据库里记录的里程数每超过15000公里倍数时，给手机发送通知告诉汽车车主需要进行维护
                    if (data.optInt("maxMileage") < Integer.valueOf(data.optString("mileage").substring(0, data.optString("mileage").length() - 2))) {
                        sendBroadcast(new Intent().setAction(Config.ACTION_MILEAGE_MUCH).putExtra(Config.KEY_MAX_MILEAGE, data.optInt("maxMileage")));
                        final VehicleInformation info = new VehicleInformation();
                        info.setMaxMileage(data.optInt("maxMileage") + 15000);
                        info.update(context, data.optString("objectId"), new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                Log.d(TAG, "修改成功" + info.getMaxMileage());
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(context, "网络不佳", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }

            @Override
            public void onConnectCompleted() {
                if (VehicleErrorNotice.isConnected()) {
                    VehicleErrorNotice.subTableUpdate("VehicleInformation");
                }
            }
        });

        return super.onStartCommand(intent, flags, startId);


    }
}
