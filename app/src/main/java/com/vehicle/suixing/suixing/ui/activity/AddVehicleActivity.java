package com.vehicle.suixing.suixing.ui.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.afun.zxinglib.ScanView.ZXingScannerViewNew;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.VehicleInformation;
import com.vehicle.suixing.suixing.ui.BaseSlidingActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/3/20.
 */
public class AddVehicleActivity extends BaseSlidingActivity implements
        ZXingScannerViewNew.ResultHandler,
        ZXingScannerViewNew.QrSize {
    private String TAG = AddVehicleActivity.class.getName();

    @OnClick(R.id.iv_back)
    void iv_toolbar_left_image() {
        finish();
        Log.d(TAG, "结束，切换activity");
    }

    @OnClick(R.id.tv_choose_in_mobile)
    void tv_choose_in_mobile() {
        Log.d(TAG, "在手机中选择图片");

    }

    ZXingScannerViewNew scanView;
    @Bind(R.id.iv_scanner_line)
    ImageView iv_scanner_line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setContentView(scanView);
        ButterKnife.bind(this);
        setupFormats();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scanView.setResultHandler(this);
//        android.hardware.Camera camera = android.hardware.Camera.open(findFrontFacingCamera());
        scanView.startCamera(0);
        scanView.setFlash(false);
        scanView.setAutoFocus(true);
    }

    private void initView() {
        scanView = new ZXingScannerViewNew(this);
        scanView.setContentView(R.layout.activity_add_vehicle);
        scanView.setQrSize(this);
    }

    @Override
    public Rect getDetectRect() {
        int top = ((View) scanView.getParent()).getTop() + scanView.getTop();
        int left = scanView.getLeft();
        int width = scanView.getWidth();
        int height = scanView.getHeight();
        Rect rect = null;
        if (width != 0 && height != 0) {
            rect = new Rect(left, top, left + width, top + height);
            addLineAnim(rect);
        }
        return rect;
    }

    private void addLineAnim(Rect rect) {
        /**
         * 加入扫描线的动画
         * */
        iv_scanner_line.setVisibility(View.VISIBLE);
        if (iv_scanner_line.getAnimation() == null) {
            TranslateAnimation anim = new TranslateAnimation(0, 0, 0, rect.height());
            anim.setDuration(1500);
            anim.setRepeatCount(Animation.INFINITE);
            iv_scanner_line.startAnimation(anim);
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        /**
         * 对扫描出的数据进行解析
         * */
        Toast.makeText(AddVehicleActivity.this, "扫描成功", Toast.LENGTH_SHORT).show();
        /**
         * 将扫描结果解析成json
         * */
        String json = rawResult.toString();
        Log.d(TAG, json);
        if (json.startsWith("{\"isYours\":\"")&&json.endsWith("}")) {
            //粗略的判断是否为我们所需的json字符
            VehicleInformation info = parseJsonWithGson(json);
            if (info.getIsYours().equals("0x110")) {
                //再次判定是否为约定字符
                Log.d(TAG, "是所需车的二维码，跳转");
                startActivity(new Intent(this,AddSuccessActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).putExtra("INFO",info));
                finish();
            } else {
                error();
            }
        } else {
            error();
        }

    }

    private void error() {
        Log.e(TAG, "不是所需车的二维码");
        Toast.makeText(AddVehicleActivity.this, "不是我们要的车啊...", Toast.LENGTH_SHORT).show();
    }

    private VehicleInformation parseJsonWithGson(String json) {
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<VehicleInformation>() {
        }.getType();
        VehicleInformation vehicleInformation = gson.fromJson(json, type);
        return vehicleInformation;
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanView.stopCamera();
    }


    public void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        formats.add(BarcodeFormat.QR_CODE);
        if (scanView != null) {
            scanView.setFormats(formats);
        }
    }
}
