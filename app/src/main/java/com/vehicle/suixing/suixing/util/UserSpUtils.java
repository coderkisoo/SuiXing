package com.vehicle.suixing.suixing.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.vehicle.suixing.suixing.bean.BmobBean.User;

/**
 * Created by KiSoo on 2016/4/11.
 */
public class UserSpUtils {
    /**
     * 存储基本信息
     * */
    public static User getUsers(Context context){
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String name = sp.getString("name", "未登录");
        String motto = sp.getString("motto", "暂无");
        String head = sp.getString("head", "");
        User user = new User();
        user.setName(name);
        user.setMotto(motto);
        user.setHead(head);
        return user;
    }
    public static void saveName(Context context,String name){
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", name);
        editor.apply();
    }
    public static void saveHead(Context context,String head){
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("head", head);
        editor.apply();
    }
    public static void saveMotto(Context context,String motto){
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("motto",motto);
        editor.apply();
    }
    public static void clearUsers(Context context){
        SharedPreferences sp = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }
}
