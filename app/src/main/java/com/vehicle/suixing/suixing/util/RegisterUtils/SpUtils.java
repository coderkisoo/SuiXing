package com.vehicle.suixing.suixing.util.RegisterUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.common.Config;

/**
 * Created by KiSoo on 2016/4/11.
 */
public class SpUtils {
    private static final String TAG = "SpUtils";
    /**
     * 存储基本信息
     */
    public static User getUsers(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Config.USER_SP, Context.MODE_PRIVATE);
        String name = sp.getString("name", "未登录");
        String motto = sp.getString("motto", "暂无");
        String head = sp.getString("head", "");
        User user = new User();
        user.setName(name);
        user.setMotto(motto);
        user.setHead(head);
        return user;
    }

    /***
     * 是否是第一次加载
     *
     * @param context
     * @return
     */
    public static boolean isFirstLoad(Context context) {
        return context.getSharedPreferences(Config.USER_SP, Context.MODE_PRIVATE).getBoolean("isFirstLoad", true);
    }

    /***
     * 更新加载状态
     *
     * @param context
     * @param load
     */
    public static void setLoad(Context context, boolean load) {
        context.getSharedPreferences(Config.USER_SP, Context.MODE_APPEND)
                .edit()
                .putBoolean("isFirstLoad", load)
                .apply();
    }

    public static void saveName(Context context, String name) {
        saveStringParams(context, "name", name);
    }

    public static void saveHead(Context context, String head) {
        saveStringParams(context, "head", head);
    }

    public static void saveMotto(Context context, String motto) {
        saveStringParams(context, "motto", motto);
    }

    private static void saveStringParams(Context context, String key, String motto) {
        context.getSharedPreferences(Config.USER_SP, Context.MODE_PRIVATE)
                .edit()
                .putString(key, motto)
                .apply();
    }

    public static void clearUsers(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Config.USER_SP, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }

    public static void saveLocation(Context context, int fabLeft, int fabTop) {
        context.getSharedPreferences(Config.USER_SP, Context.MODE_PRIVATE)
                .edit()
                .putInt(Config.KEY_LEFT, fabLeft)
                .putInt(Config.KEY_TOP, fabTop)
                .apply();
        Log.d(TAG, "已经存储（" + fabLeft + "，" + fabTop + ")");
    }

    public static int[] getLocation(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Config.USER_SP, Context.MODE_PRIVATE);
        return new int[]{sp.getInt(Config.KEY_LEFT, -1), sp.getInt(Config.KEY_TOP, -1)};
    }
}
