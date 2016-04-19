package com.vehicle.suixing.suixing.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.ui.BaseSlidingActivity;
import com.vehicle.suixing.suixing.util.BmobError;
import com.vehicle.suixing.suixing.util.DensityUtil;
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
public class EditMottoActivity extends BaseSlidingActivity {

    @Bind(R.id.et_motto)
    EditText et_motto;
    @Bind(R.id.tv_text_num)
    TextView tv_text_num;
    @OnClick(R.id.iv_toolbar_left_image)
    void back(){
        ifBack();
    }

    @OnClick(R.id.iv_cancel)
    void iv_cancel() {
        et_motto.setText("");
    }

    @OnClick(R.id.tv_complete)
    void tv_compelete() {
        final ProgressDialog dialog = new ProgressDialog(EditMottoActivity.this);
        dialog.setTitle("提示:");
        dialog.setMessage("正在修改中...");
        dialog.show();
        User user = new User();
        user.setMotto(et_motto.getText().toString());
        user.update(EditMottoActivity.this, BmobUser.getCurrentUser(this,User.class).getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                dialog.dismiss();
                Toast.makeText(EditMottoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                UserSpUtils.saveMotto(EditMottoActivity.this, et_motto.getText().toString());
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(EditMottoActivity.this, BmobError.error(i), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_motto);
        ButterKnife.bind(this);
        final NameLengthFilter inputFilter = new NameLengthFilter(100,EditMottoActivity.this);
        et_motto.setFilters(new InputFilter[]{inputFilter});
        et_motto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = et_motto.getText().toString();
                tv_text_num.setText(str.length()+ DensityUtil.getChineseCount(str)+"/100");
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        et_motto.setText(UserSpUtils.getUsers(this).getMotto());
        CharSequence text = et_motto.getText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
        et_motto.setFocusable(true);
        et_motto.setFocusableInTouchMode(true);
        et_motto.requestFocus();


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
