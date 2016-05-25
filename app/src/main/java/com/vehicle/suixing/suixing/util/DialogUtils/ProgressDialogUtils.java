package com.vehicle.suixing.suixing.util.DialogUtils;


import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by KiSoo on 2016/5/12.
 */
public class ProgressDialogUtils {
    private static Builder builder;
    private static ProgressDialog dialog;
    private ProgressDialogUtils(){

    }
    public static Builder from(Context context){
        builder = new Builder(context);
        return builder;
    }
    public static class Builder {
        private Builder(Context context){
            dialog = new ProgressDialog(context);
        }
        public Builder setTitle(String title){
            dialog.setTitle(title);
            return builder;
        }
        public Builder setMessage(String message){
            dialog.setMessage(message);
            return builder;
        }
        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside){
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
            return builder;
        }
        public ProgressDialog show(){
            dialog.show();
            return dialog;
        }
    }
}
