package com.vehicle.suixing.suixing.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vehicle.suixing.suixing.R;


/**
 * Created by KiSoo on 2016/3/17.
 */
public class BaseActivity extends AppCompatActivity {
    /**
     * 初始化toolbar，包括更新toolbar上的textview
     */
    public void initToolbar(android.support.v7.widget.Toolbar mToolbar, int resId, String activityName, Boolean isShow) {
        mToolbar.setTitle("");
        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_text);
        toolbar_title.setText(activityName);
        ImageView iv_toolbar_left_image = (ImageView) findViewById(R.id.iv_toolbar_left_image);
        iv_toolbar_left_image.setImageResource(resId);
        LinearLayout ll_location = (LinearLayout) findViewById(R.id.ll_location);
        if (isShow) {
            ll_location.setVisibility(View.VISIBLE);
        } else {
            ll_location.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /**
         * 完全沉浸式布局
         * */
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            /**
             * 底部菜单栏
             * */
//            window.setNavigationBarColor(Color.WHITE);
        }
    }

    private void initToolbar(android.support.v7.widget.Toolbar mToolBar) {
        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
    }
}
