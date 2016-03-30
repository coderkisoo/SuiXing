package com.vehicle.suixing.suixing.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.app.SuiXingApplication;
import com.vehicle.suixing.suixing.ui.BaseSlidingActivity;
import com.vehicle.suixing.suixing.util.BmobError;
import com.vehicle.suixing.suixing.util.RegisterUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;

/**
 * Created by KiSoo on 2016/3/28.
 */
public class RegisterActivity extends BaseSlidingActivity {
    private static final int AUTH_CODING = 0;
    private static final int AUTH_CODED = 1;
    private int WAIT_SECONDS = 100;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                /**
                 * 改变ui
                 * */
                case AUTH_CODING:
                    tv_authcode.setClickable(false);
                    tv_authcode.setBackgroundResource(R.mipmap.clicked);
                    tv_authcode.setText("重新发送" + msg.arg1 + "s");
                    break;
                case AUTH_CODED:
                    tv_authcode.setClickable(true);
                    tv_authcode.setBackgroundResource(R.mipmap.send_authcode);
                    tv_authcode.setText("重新发送");
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

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
        final ProgressDialog dialog = ProgressDialog.show(RegisterActivity.this, "提示", "正在发送短信中...");
        BmobSMS.requestSMSCode(RegisterActivity.this, et_tel.getText().toString(), "AuthCode", new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                dialog.dismiss();
                if (e == null) {
                    Log.i("smile", "短信id：" + integer);//用于查询本次短信发送详情
                    changeUI();
                } else {
                    String error = BmobError.error(e.getErrorCode());
                    if (e.equals("")) {
                        Log.e(TAG, e.getMessage() + e.getErrorCode());
                        toast(e.getMessage());
                    } else {
                        toast(error);
                    }

                }
            }
        });
    }

    @OnClick(R.id.tv_register)
    void tv_register() {
        /**
         * 注册
         * */
        RegisterUtil.register(RegisterActivity.this,
                et_username.getText().toString(),
                et_password1.getText().toString(),
                et_password2.getText().toString(),
                et_authcode.getText().toString(),
                et_tel.getText().toString());
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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);
    }



    /**
     * 发送验证码改变按键UI
     */
    private void changeUI() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int m = WAIT_SECONDS;
                while (m > 1) {
                    Message authCoding = new Message();
                    authCoding.what = AUTH_CODING;
                    authCoding.arg1 = m;
                    handler.sendMessage(authCoding);
                    Log.v(TAG, "更新秒数" + m + "s");
                    try {
                        Thread.sleep(1000);
                        Log.v(TAG, "sleep" + m + "s");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.e(TAG, "sleep失败");
                    }
                    m = m - 1;
                }
                Message authCoded = new Message();
                authCoded.what = AUTH_CODED;
                handler.sendMessage(authCoded);
            }
        }).start();
    }


}
