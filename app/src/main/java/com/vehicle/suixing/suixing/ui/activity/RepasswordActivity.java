package com.vehicle.suixing.suixing.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.ui.BaseSlidingActivity;
import com.vehicle.suixing.suixing.util.AuthCodeUtil;
import com.vehicle.suixing.suixing.util.ForgetUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/3/28.
 */
public class RepasswordActivity extends BaseSlidingActivity {
    private static final int AUTH_CODING = 0;
    private static final int AUTH_CODED = 1;



    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                /**
                 * 改变ui
                 * */
                case AUTH_CODING:
                    tv_send_authcode.setClickable(false);
                    tv_send_authcode.setBackgroundResource(R.mipmap.clicked);
                    tv_send_authcode.setText("重新发送" + msg.arg1 + "s");
                    break;
                case AUTH_CODED:
                    tv_send_authcode.setClickable(true);
                    tv_send_authcode.setBackgroundResource(R.mipmap.send_authcode);
                    tv_send_authcode.setText("重新发送");
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };



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
        ForgetUtil.register(RepasswordActivity.this,
                et_password1.getText().toString(),
                et_password2.getText().toString(),
                et_authcode.getText().toString(),
                et_tel.getText().toString());

    }


    @OnClick(R.id.tv_send_authcode)
    void tv_send_authcode() {
        /**
         * 发送验证码
         * */
        AuthCodeUtil.sendAuthCode(RepasswordActivity.this,et_tel.getText().toString(),handler);
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
    }
}
