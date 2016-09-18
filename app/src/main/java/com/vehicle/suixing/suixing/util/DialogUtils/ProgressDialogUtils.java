package com.vehicle.suixing.suixing.util.DialogUtils;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by KiSoo on 2016/5/12.
 */
public class ProgressDialogUtils {

    private ProgressDialogUtils(){

    }
    public static Builder from(Context context){
        return new Builder(context);
    }
    public static class Builder {
        private static ProgressDialog dialog;

        private Builder(Context context){
            dialog = new ProgressDialog(context);
        }
        public Builder setTitle(String title){
            dialog.setTitle(title);
            return this;
        }
        public Builder setMessage(String message){
            dialog.setMessage(message);
            return this;
        }
        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside){
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
            return this;
        }
        public ProgressDialog show(){
            dialog.show();
            return dialog;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener){
            dialog.setOnDismissListener(onDismissListener);
            return this;
        }
    }
}
