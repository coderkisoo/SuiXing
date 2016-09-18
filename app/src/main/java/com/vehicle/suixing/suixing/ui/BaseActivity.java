package com.vehicle.suixing.suixing.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.app.SuixingApp;


/**
 * Created by KiSoo on 2016/3/17.
 */
public class BaseActivity extends AppCompatActivity {
    protected Activity mContext;
    /**
     * 初始化toolbar，包括更新toolbar上的textview
     */
    public void initToolbar(android.support.v7.widget.Toolbar mToolbar, String activityName, Boolean showMe,Boolean showSearch) {
        TextView toolbar_title = (TextView) mToolbar.findViewById(R.id.toolbar_text);
        ImageView iv_me = (ImageView) mToolbar.findViewById(R.id.iv_me);
        ImageView iv_search = (ImageView)mToolbar.findViewById(R.id.iv_search);
        toolbar_title.setText(activityName);
        iv_me.setVisibility(showMe?View.VISIBLE:View.GONE);
        iv_search.setVisibility(showSearch?View.VISIBLE:View.GONE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        /**
         * 完全沉浸式布局
         * */
        SuixingApp.addActivity(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        /**
         * 取消了完全沉浸式，不理想
         * */
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(
//                    Color.TRANSPARENT
//                    Color.parseColor("#30000000")
//            );
//        }
    }


    private void initToolbar(android.support.v7.widget.Toolbar mToolBar) {
        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void finish() {
        super.finish();
        /**
         * 让软键盘消失
         * */
        SuixingApp.removeActivity(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}
