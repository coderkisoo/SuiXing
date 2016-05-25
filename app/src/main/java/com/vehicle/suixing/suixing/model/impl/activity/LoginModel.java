package com.vehicle.suixing.suixing.model.impl.activity;

import android.content.Context;

import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.callback.BmobListener;
import com.vehicle.suixing.suixing.model.activity.ILoginModel;
import com.vehicle.suixing.suixing.util.formatUtils.MD5Utils;
import com.vehicle.suixing.suixing.util.RegisterUtils.SaveUser;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by KiSoo on 2016/4/20.
 */
public class LoginModel implements ILoginModel {
    @Override
    public void login(final Context context,String username, final String password, final BmobListener listener) {
        User user = new User();
        if (!SaveUser.isTel(username)){
            /**
             * 如果不是手机号码 再进行加密
             * */
            username = MD5Utils.ecoder(username);
        }
        final String finalpassword = MD5Utils.ecoder(password);
        final String finalUsername = username;
        user.setUsername(finalUsername);
        user.setPassword(finalpassword);
        user.login(context, new SaveListener() {
            @Override
            public void onSuccess() {
                SaveUser.save(finalUsername, finalpassword, context);
                listener.onSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                listener.onFailure(i,s);
            }
        });
    }
}
