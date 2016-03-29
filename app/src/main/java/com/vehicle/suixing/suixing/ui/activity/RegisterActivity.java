package com.vehicle.suixing.suixing.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.ui.BaseSlidingActivity;

import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/3/28.
 */
public class RegisterActivity extends BaseSlidingActivity {
    @OnClick(R.id.iv_toolbar_left_image)
    void back(){
        finish();
    }
    @OnClick(R.id.tv_use_free)
    void tv_use_free(){
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
    }
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

}
