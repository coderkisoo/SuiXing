package com.vehicle.suixing.suixing.util.RegisterUtils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.callback.UpdateList;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.util.dataBase.DbDao;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by KiSoo on 2016/4/18.
 */
public class BmobUtils {
    private static String TAG = BmobUtils.class.getName();

    public static void updateList(final Context context, final UpdateList listener) {
        BmobQuery<VehicleInformation> query = new BmobQuery<>();
        query.addWhereEqualTo("username", Config.USERNAME);
        query.findObjects(context, new FindListener<VehicleInformation>() {
            @Override
            public void onSuccess(List<VehicleInformation> list) {
                Log.e(TAG, "成功");
                for (VehicleInformation info : list) {
                    DbDao.add(context, info);
                }
                listener.updateList(list);
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(context,BmobError.error(i),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
