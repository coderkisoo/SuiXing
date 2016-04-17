package com.vehicle.suixing.suixing.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KiSoo on 2016/3/31.
 */
public class SaveUser {
    /**
     * 存储登陆信息
     * */
    public static void save(String username, String password, Context context) {
        /**
         * 用于存储本地信息
         * */
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }

    public static List<String> getUser(Context context) {
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        List<String> userInfo = new ArrayList<>();
        userInfo.add(sp.getString("username", ""));
        userInfo.add(sp.getString("password", ""));
        return userInfo;
    }

    public static boolean isTel(String username) {
        if (username.matches("^1[345678]\\d{9}$"))
            return true;
        return false;
    }
}
