package com.vehicle.suixing.suixing.presenter.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.vehicle.suixing.suixing.callback.BmobListener;
import com.vehicle.suixing.suixing.model.activity.ILoginModel;
import com.vehicle.suixing.suixing.model.impl.activity.LoginModel;
import com.vehicle.suixing.suixing.ui.activity.MainActivity;
import com.vehicle.suixing.suixing.ui.activity.RepasswordActivity;
import com.vehicle.suixing.suixing.util.RegisterUtils.BmobError;
import com.vehicle.suixing.suixing.view.activity.LoginActivityView;

/**
 * Created by KiSoo on 2016/4/4.
 */
public class LoginActivityPresenter {

    private LoginActivityView view;
    private ILoginModel model;
    private Context context;

    public LoginActivityPresenter(LoginActivityView view, Context context) {
        this.view = view;
        this.context = context;
        this.model = new LoginModel();
    }
    /**
     * 点击登录
     * */
    public void Login(){

        final ProgressDialog progressDialog = ProgressDialog.show(context,"提示","正在登录中，请稍后...");
        model.login(context,view.getUsername(),view.getPassword(), new BmobListener() {
            @Override
            public void onSuccess() {
                progressDialog.dismiss();
                showToast("登录成功");
                context.startActivity(new Intent(context,MainActivity.class));
            }

            @Override
            public void onFailure(int i, String s) {
                progressDialog.dismiss();
                showToast(BmobError.error(i));
            }
        });

    }
    /**
     * 随便看看
     * */
    public void useFree(){
        context.startActivity(new Intent(context, MainActivity.class));
    }
    /**
     * 忘记密码
     * */
    public void forgetPassword(){
        context.startActivity(new Intent(context, RepasswordActivity.class));

    }
    private void showToast(String toast){
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();

    }
}
