package com.vehicle.suixing.suixing.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.vehicle.suixing.suixing.bean.BmobBean.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.ResetPasswordByCodeListener;

/**
 * Created by KiSoo on 2016/3/31.
 */
public class ForgetUtil {
    /**
     * 判断注册所用的逻辑
     */
    private static String TAG = ForgetUtil.class.getName();

    public static void register(final Context context, final String password1, String password2, final String authcode, final String tel) {
        //判断是我们要的验证码
        if (authcode.length() != 6) {
            Toast.makeText(context, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        //判断密码长度大于等于6位
        if (password1.length() < 6) {
            Toast.makeText(context, "密码长度要大于6位哦！", Toast.LENGTH_SHORT).show();
            return;
        }
        //判断两次输入的密码相同
        if (!TextUtils.equals(password1, password2)) {
            Toast.makeText(context, "两次输入的密码不太一样哦", Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog dialog = ProgressDialog.show(context, "提示", "正在修改中...");
        User.resetPasswordBySMSCode(context, authcode, password1, new ResetPasswordByCodeListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    Toast.makeText(context,"重置密码成功",Toast.LENGTH_SHORT).show();
                }else {
                    Log.e(TAG,"重置密码失败"+e.getMessage()+e.getErrorCode());
                    String error = BmobError.error(e.getErrorCode());
                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private static boolean startWithLetter(String s) {
        char c;
        c = s.charAt(0);
        if (c >= 'A' && c <= 'z') {
            return true;
        } else {
            return false;
        }
    }
}
