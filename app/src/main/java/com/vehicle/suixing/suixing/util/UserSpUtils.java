package com.vehicle.suixing.suixing.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.vehicle.suixing.suixing.bean.BmobBean.Users;

/**
 * Created by KiSoo on 2016/4/11.
 */
public class UserSpUtils {
    public static Users getUsers(Context context){
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String name = sp.getString("name", "未登录");
        String motto = sp.getString("motto", "暂无");
        String head = sp.getString("head", "");
        Users users = new Users();
        users.setName(name);
        users.setMotto(motto);
        users.setHead(head);
        return users;
    }
}
