package com.vehicle.suixing.suixing.ui.activity;

import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.afun.zxinglib.ScanView.ZXingScannerViewNew;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.ui.BaseSlidingActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/3/20.
 */
public class AddVehicleActivity extends BaseSlidingActivity implements ZXingScannerViewNew.ResultHandler, ZXingScannerViewNew.QrSize {
    private String TAG = AddVehicleActivity.class.getName();
    @OnClick(R.id.iv_back)
    void iv_toolbar_left_image(){
        finish();
        Log.d(TAG, "结束，切换activity");
    }
    @OnClick(R.id.tv_choose_in_mobile)
    void tv_choose_in_mobile(){
        Log.d(TAG,"在手机中选择图片");

    }
    private ZXingScannerViewNew scanView;
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
        android.hardware.Camera camera = android.hardware.Camera.open(findFrontFacingCamera());
        scanView.startCamera(camera);
        scanView.setFlash(false);
        scanView.setAutoFocus(true);
    }

    private void initView() {
        scanView = new ZXingScannerViewNew(this);
        scanView.setContentView(R.layout.activity_add_vehicle);
        scanView.setQrSize(this);
    }
    public void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        formats.add(BarcodeFormat.QR_CODE);
        if (scanView != null) {
            scanView.setFormats(formats);
        }
    }
    @Override
    public Rect getDetectRect() {
        View rl_scan_window = findViewById(R.id.rl_scan_window);
        int top = ((View) rl_scan_window.getParent()).getTop() + rl_scan_window.getTop();
        int left = rl_scan_window.getLeft();
        int width = rl_scan_window.getWidth();
        int height = rl_scan_window.getHeight();
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

    }

    @Override
    protected void onPause() {
        super.onPause();
        scanView.stopCamera();
    }

    private int findFrontFacingCamera() {
        int cameraId = 0;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            boolean back = false;
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                back = true;
                break;
            }
        }
        Log.e(TAG,"最终的cameraId是"+cameraId);
        return cameraId;
    }

}
