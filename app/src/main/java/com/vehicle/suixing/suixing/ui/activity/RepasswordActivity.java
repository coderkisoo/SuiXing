package com.vehicle.suixing.suixing.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.ui.BaseSlidingActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/3/28.
 */
public class RepasswordActivity extends BaseSlidingActivity {
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
        if (et_password1.getText().toString().length() < 8) {
            Toast.makeText(RepasswordActivity.this, "密码长度要大于八位哦", Toast.LENGTH_SHORT).show();
        } else if (!TextUtils.equals(et_password1.getText().toString(), et_password2.getText().toString())) {
            tv_nosame.setVisibility(View.VISIBLE);
            Toast.makeText(RepasswordActivity.this, "两次输入的密码不太一样，请检查", Toast.LENGTH_SHORT).show();
        } else {

        }
    }

    @OnClick(R.id.tv_use_free)
    void tv_use_free() {
        /**
         * 随便看看
         * */
        startActivity(new Intent(RepasswordActivity.this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.tv_send_authcode)
    void tv_send_authcode() {
        /**
         * 发送验证码
         * */
    }

    @OnClick({R.id.et_password1, R.id.et_password2})
    void vis() {
        tv_nosame.setVisibility(View.INVISIBLE);
    }

    @Bind(R.id.et_username)
    EditText et_username;//用户名

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
    }

}
