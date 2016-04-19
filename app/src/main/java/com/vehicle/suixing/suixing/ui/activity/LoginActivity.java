package com.vehicle.suixing.suixing.ui.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.view.activity.LoginActivityView;
import com.vehicle.suixing.suixing.presenter.LoginActivityPresenter;
import com.vehicle.suixing.suixing.ui.BaseSlidingActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/3/28.
 */
public class LoginActivity extends BaseSlidingActivity implements LoginActivityView{
    private String TAG = LoginActivity.class.getName();
    private LoginActivityPresenter presenter;
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
        presenter.useFree();
    }
    @OnClick(R.id.tv_forget_password)
    void tv_forget_password(){
        presenter.forgetPassword();
    }
    @OnClick(R.id.tv_login)
    void tv_login(){
        presenter.Login();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LoginActivityPresenter(this,this);
    }


    @Override
    public String getUsername() {
        return et_username.getText().toString();
    }

    @Override
    public String getPassword() {
        return et_password.getText().toString();
    }

}
