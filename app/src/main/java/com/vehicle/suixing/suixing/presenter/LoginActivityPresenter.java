package com.vehicle.suixing.suixing.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.model.LoginActivityView;
import com.vehicle.suixing.suixing.ui.activity.MainActivity;
import com.vehicle.suixing.suixing.ui.activity.RepasswordActivity;
import com.vehicle.suixing.suixing.util.BmobError;
import com.vehicle.suixing.suixing.util.MD5Utils;
import com.vehicle.suixing.suixing.util.SaveUser;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by KiSoo on 2016/4/4.
 */
public class LoginActivityPresenter {

    private LoginActivityView view;

    private Context context;

    public LoginActivityPresenter(LoginActivityView view, Context context) {
        this.view = view;
        this.context = context;
    }
    public void Login(){
        User user = new User();
        String username = view.getUsername();
        if (!SaveUser.isTel(username)){
            /**
             * 如果不是手机号码 再进行加密
             * */
            username = MD5Utils.ecoder(username);
        }
        final String password = MD5Utils.ecoder(view.getPassword());
        final String finalUsername = username;
        user.setUsername(username);
        user.setPassword(password);
        final ProgressDialog progressDialog = ProgressDialog.show(context,"提示","正在登录中，请稍后...");
        user.login(context, new SaveListener() {
            @Override
            public void onSuccess() {
                progressDialog.dismiss();
                showToast("登录成功");
                SaveUser.save(finalUsername,password,context);
                context.startActivity(new Intent(context,MainActivity.class));
            }

            @Override
            public void onFailure(int i, String s) {
                progressDialog.dismiss();
                String error = BmobError.error(i);
                if (error.equals("")){
                    showToast(s);
                }else {
                    showToast(error);
                }
            }
        });
    }
    /**
     * 随便看看
     * */
    public void useFree(){
        context.startActivity(new Intent(context, MainActivity.class));
    }
    public void forgetPassword(){
        context.startActivity(new Intent(context, RepasswordActivity.class));

    }
    private void showToast(String toast){
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();

    }
}
