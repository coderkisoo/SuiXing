package com.vehicle.suixing.suixing.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vehicle.suixing.suixing.R;


/**
 * Created by KiSoo on 2016/3/17.
 */
public class BaseActivity extends AppCompatActivity {
    public void initToolbar(android.support.v7.widget.Toolbar mToolbar,int resId,String activityName, Boolean isShow) {
        mToolbar.setTitle("");
        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_text);
        toolbar_title.setText(activityName);
        ImageView iv_toolbar_left_image = (ImageView) findViewById(R.id.iv_toolbar_left_image);
        iv_toolbar_left_image.setImageResource(resId);
        LinearLayout ll_location = (LinearLayout) findViewById(R.id.ll_location);
        if (isShow){
            ll_location.setVisibility(View.VISIBLE);
        }else {
            ll_location.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    private void initToolbar(android.support.v7.widget.Toolbar mToolBar) {
        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
    }
}
