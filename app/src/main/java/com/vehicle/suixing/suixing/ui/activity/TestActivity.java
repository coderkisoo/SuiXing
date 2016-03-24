package com.vehicle.suixing.suixing.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.vehicle.suixing.suixing.R;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by KiSoo on 2016/3/22.
 */
public class TestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        ImageView imageView = (ImageView) findViewById(R.id.text_img);
        BmobFile bmobFile = new BmobFile();
        bmobFile.setUrl(getIntent().getStringExtra("0"));
        bmobFile.loadImage(TestActivity.this,imageView);
    }
}
