package com.vehicle.suixing.suixing.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;

/**
 * Created by KiSoo on 2016/3/31.
 */
public class AuthCodeUtil {
    private static String TAG = AuthCodeUtil.class.getName();
    private static int WAIT_SECONDS = 100;
    private static final int AUTH_CODING = 0;
    private static final int AUTH_CODED = 1;

    /**
     * 这个方法用来发送验证码
     */
    public static void sendAuthCode(final Context context, String phoneNum, final Handler handler) {
        final ProgressDialog dialog = ProgressDialog.show(context, "提示", "正在发送短信中...");
        BmobSMS.requestSMSCode(context, phoneNum, "AuthCode", new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                dialog.dismiss();
                if (e == null) {
                    Log.i("smile", "短信id：" + integer);//用于查询本次短信发送详情
                    changeUI(handler);
                } else {
                    String error = BmobError.error(e.getErrorCode());
                    if (e.equals("")) {
                        Log.e(TAG, e.getMessage() + e.getErrorCode());
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    private static void changeUI(final Handler handler) {
        /**
         * 这个方法用来改变UI
         * */
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
