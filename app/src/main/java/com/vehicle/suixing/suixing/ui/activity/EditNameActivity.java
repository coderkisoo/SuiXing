package com.vehicle.suixing.suixing.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spannable;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.ui.BaseSlidingActivity;
import com.vehicle.suixing.suixing.util.BmobError;
import com.vehicle.suixing.suixing.util.NameLengthFilter;
import com.vehicle.suixing.suixing.util.UserSpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by KiSoo on 2016/2/12.
 */
public class EditNameActivity extends BaseSlidingActivity {

    @Bind(R.id.et_name)
    EditText et_name;





    @OnClick(R.id.iv_cancel)
     void iv_cancel() {
        et_name.setText("");
    }

    @OnClick(R.id.tv_complete)
    void tv_compelete() {
        if (et_name.getText().toString().length() <= 3) {
            Toast.makeText(EditNameActivity.this, "昵称长度不够( ⊙ o ⊙ )！", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog dialog = new ProgressDialog(EditNameActivity.this);
            dialog.setTitle("提示:");
            dialog.setMessage("正在修改中...");
            dialog.show();
            User user = new User();
            user.setName(et_name.getText().toString());
            user.update(EditNameActivity.this, BmobUser.getCurrentUser(this,User.class).getObjectId(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    dialog.dismiss();
                    Toast.makeText(EditNameActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    UserSpUtils.saveName(EditNameActivity.this, et_name.getText().toString());
                    finish();
                }

                @Override
                public void onFailure(int i, String s) {
                    dialog.dismiss();
                    Toast.makeText(EditNameActivity.this, BmobError.error(i), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_name);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ButterKnife.bind(this);
        et_name.setText(UserSpUtils.getUsers(this).getName());
        CharSequence text = et_name.getText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }

        et_name.setFilters(new InputFilter[]{new NameLengthFilter(20,EditNameActivity.this)});
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ifBack();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
