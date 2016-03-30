package com.vehicle.suixing.suixing.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.ui.BaseSlidingActivity;
import com.vehicle.suixing.suixing.util.BmobError;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by KiSoo on 2016/3/28.
 */
public class LoginActivity extends BaseSlidingActivity {
    private String TAG = LoginActivity.class.getName();
    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_password)
    EditText et_password;

    @OnClick(R.id.iv_toolbar_left_image)
    void back(){
        finish();
    }
    @OnClick(R.id.tv_use_free)
    void tv_use_free(){
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
    @OnClick(R.id.tv_forget_password)
    void tv_forget_password(){
        startActivity(new Intent(LoginActivity.this, RepasswordActivity.class));
    }
    @OnClick(R.id.tv_login)
    void tv_login(){
        User user = new User();
        user.setUsername(et_username.getText().toString());
        user.setPassword(et_password.getText().toString());
        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this,"提示","正在登录中，请稍后...");
        user.login(LoginActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                progressDialog.dismiss();
                toast("登录成功");
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }

            @Override
            public void onFailure(int i, String s) {
                progressDialog.dismiss();
                String error = BmobError.error(i);
                if (error.equals("")){
                    toast(s);
                }else {
                    toast(error);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }


}
