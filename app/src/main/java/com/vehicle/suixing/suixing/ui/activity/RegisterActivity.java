package com.vehicle.suixing.suixing.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.view.activity.RegisterActivityView;
import com.vehicle.suixing.suixing.presenter.RegisterActivityPresenter;
import com.vehicle.suixing.suixing.ui.BaseSlidingActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/3/28.
 */
public class RegisterActivity extends BaseSlidingActivity implements RegisterActivityView {

    private int WAIT_SECONDS = 100;
    private RegisterActivityPresenter presenter;


    @OnClick(R.id.iv_toolbar_left_image)
    void back() {
        /**
         * 返回键
         * */
        finish();
    }

    @OnClick(R.id.tv_authcode)
    void sendAuthCode() {
        /**
         * 发送验证码
         * */
        presenter.sendAuth();
    }

    @OnClick(R.id.tv_register)
    void tv_register() {
        /**
         * 注册
         * */
        presenter.register();
    }


    @Bind(R.id.tv_authcode)
    TextView tv_authcode;
    @Bind(R.id.et_tel)
    EditText et_tel;
    @Bind(R.id.tv_nosame)
    TextView tv_nosame;
    @Bind(R.id.et_password1)
    EditText et_password1;
    @Bind(R.id.et_password2)
    EditText et_password2;
    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_authcode)
    EditText et_authcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        /**
         * 软键盘状态
         * */
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);
        presenter = new RegisterActivityPresenter(this, this);
    }


    @Override
    public String getUsername() {
        return et_username.getText().toString();
    }

    @Override
    public String getPassword1() {
        return et_password1.getText().toString();
    }

    @Override
    public String getPassword2() {
        return et_password2.getText().toString();
    }

    @Override
    public String getAuthCode() {
        return et_authcode.getText().toString();
    }

    @Override
    public String getTel() {
        return et_tel.getText().toString();
    }

    @Override
    public void setClickable(Boolean clickable) {
        tv_authcode.setClickable(clickable);
    }

    @Override
    public void sending(int seconds) {
        tv_authcode.setBackgroundResource(R.mipmap.clicked);
        tv_authcode.setText("重新发送" + seconds + "s");

    }

    @Override
    public void sendable() {
        tv_authcode.setBackgroundResource(R.mipmap.send_authcode);
        tv_authcode.setText("重新发送");
    }
}
