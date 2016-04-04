package com.vehicle.suixing.suixing.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.model.RegisterActivityView;
import com.vehicle.suixing.suixing.presenter.RepasswordActivityPresenter;
import com.vehicle.suixing.suixing.ui.BaseSlidingActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/3/28.
 */
public class RepasswordActivity extends BaseSlidingActivity implements RegisterActivityView {

    private RepasswordActivityPresenter presenter;


    @OnClick(R.id.iv_toolbar_left_image)
    void back() {
        /**
         * 返回
         * */
        finish();
    }

    @OnClick(R.id.tv_edit)
    void tv_edit() {
        /**
         * 确认修改
         * */
        presenter.repassword();
    }


    @OnClick(R.id.tv_send_authcode)
    void tv_send_authcode() {
        /**
         * 发送验证码
         * */
        presenter.sendAuth();
    }

    @OnClick({R.id.et_password1, R.id.et_password2})
    void vis() {
        tv_nosame.setVisibility(View.INVISIBLE);
    }


    @Bind(R.id.tv_send_authcode)
    TextView tv_send_authcode;//验证码

    @Bind(R.id.et_tel)
    EditText et_tel;

    @Bind(R.id.et_password1)
    EditText et_password1;//第一次输入

    @Bind(R.id.et_password2)
    EditText et_password2;//重复密码

    @Bind(R.id.et_authcode)
    EditText et_authcode;//验证码

    @Bind(R.id.tv_nosame)
    TextView tv_nosame;//两次输入的密码不一样

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        ButterKnife.bind(this);
        presenter = new RepasswordActivityPresenter(this, this);
    }

    @Override
    public String getUsername() {
        return null;
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
        tv_send_authcode.setBackgroundResource(R.mipmap.send_authcode);
        tv_send_authcode.setText("重新发送");
    }

    @Override
    public void sending(int seconds) {
        tv_send_authcode.setBackgroundResource(R.mipmap.clicked);
        tv_send_authcode.setText("重新发送" + seconds + "s");
    }

    @Override
    public void sendable() {
        tv_send_authcode.setBackgroundResource(R.mipmap.send_authcode);
        tv_send_authcode.setText("重新发送");
    }
}
