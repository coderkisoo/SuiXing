package com.vehicle.suixing.suixing.presenter.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.ui.activity.MainActivity;
import com.vehicle.suixing.suixing.util.Log;
import com.vehicle.suixing.suixing.util.RegisterUtils.AuthCodeUtil;
import com.vehicle.suixing.suixing.util.RegisterUtils.BmobError;
import com.vehicle.suixing.suixing.util.RegisterUtils.SaveUser;
import com.vehicle.suixing.suixing.view.activity.RegisterActivityView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.ResetPasswordByCodeListener;

/**
 * Created by KiSoo on 2016/4/4.
 */
public class RepasswordActivityPresenter {
    private static final int AUTH_CODING = 0;
    private static final int AUTH_CODED = 1;
    private String TAG = this.getClass().getName();
    private RegisterActivityView view;
    private Context context;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                /**
                 * 改变ui
                 * */
                case AUTH_CODING:
                    view.setClickable(false);
                    view.sending(msg.arg1);
                    break;
                case AUTH_CODED:
                    view.setClickable(true);
                   view.sendable();
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public RepasswordActivityPresenter(RegisterActivityView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void sendAuth(){
        AuthCodeUtil.sendAuthCode(context,view.getTel().toString(), handler);
    }

    public void repassword() {
        //判断是我们要的验证码
        if (view.getAuthCode().length() != 6) {
            Toast.makeText(context, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        //判断密码长度大于等于6位
        if (view.getPassword1().length() < 6) {
            Toast.makeText(context, "密码长度要大于6位哦！", Toast.LENGTH_SHORT).show();
            return;
        }
        //判断两次输入的密码相同
        if (!TextUtils.equals(view.getPassword1(), view.getPassword2())) {
            Toast.makeText(context, "两次输入的密码不太一样哦", Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog dialog = ProgressDialog.show(context, "提示", "正在修改中...");
        User.resetPasswordBySMSCode(context, view.getAuthCode(), view.getPassword1(), new ResetPasswordByCodeListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "重置密码成功", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    SaveUser.save(view.getTel(), view.getPassword1(), context);
                    context.startActivity(new Intent(context, MainActivity.class));
                } else {
                    Log.e(TAG, "重置密码失败" + e.getMessage() + e.getErrorCode());
                    String error = BmobError.error(e.getErrorCode());
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

    }


}
