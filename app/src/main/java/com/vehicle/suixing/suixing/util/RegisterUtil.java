package com.vehicle.suixing.suixing.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.ui.activity.MainActivity;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * Created by KiSoo on 2016/3/31.
 */
public class RegisterUtil {
    /**
     * 判断注册所用的逻辑
     */
    private static String TAG = RegisterUtil.class.getName();

    public static void register(final Context context, final String username, final String password1, String password2, String authcode, final String tel) {
        //判断是我们要的验证码
        if (authcode.length() != 6) {
            Toast.makeText(context, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        //判断是否以字母开头
        if (!startWithLetter(username)) {
            Toast.makeText(context, "用户名要以字母开头哦！", Toast.LENGTH_SHORT).show();
            return;
        }
        //判断用户名长度
        if (username.length() < 8) {
            Toast.makeText(context, "用户名长度要大于8位", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog dialog = ProgressDialog.show(context, "提示", "正在注册中...");
        BmobSMS.verifySmsCode(context, tel, authcode, new VerifySMSCodeListener() {
            @Override
            public void done(final BmobException e) {
                if (e == null) {
                    /**
                     * 验证通过，注册成功
                     * */
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password1);
                    user.setMobilePhoneNumber(tel);
                    user.setName("待修改");
                    user.setHead(BmobFile.createEmptyFile());
                    user.setMotto("待修改");
                    user.signUp(context, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            /**
                             * 注册成功
                             * */
                            Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            context.startActivity(new Intent(context, MainActivity.class));
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            /**
                             * 注册失败
                             * */
                            String error = BmobError.error(i);
                            if (error.equals("")) {
                                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                            }
                            Log.e(TAG, s + "错误码" + i);
                        }
                    });
                } else {
                    /**
                     * 验证失败，注册失败
                     * */
                    dialog.dismiss();
                    String error = BmobError.error(e.getErrorCode());
                    if (error.equals("")) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    }
                    Log.e(TAG, e.getMessage() + "错误码" + e.getErrorCode());
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
