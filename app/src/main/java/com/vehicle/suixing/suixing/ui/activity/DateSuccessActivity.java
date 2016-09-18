package com.vehicle.suixing.suixing.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.MapBean.MapInfo;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.ui.BaseSlidingActivity;
import com.vehicle.suixing.suixing.util.DialogUtils.ProgressDialogUtils;
import com.vehicle.suixing.suixing.util.Log;
import com.vehicle.suixing.suixing.util.formatUtils.DensityUtil;
import com.vehicle.suixing.suixing.util.formatUtils.QRCodeUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/5/16.
 */
public class DateSuccessActivity extends BaseSlidingActivity {
    @OnClick(R.id.iv_toolbar_left_image)
    void iv_toolbar_left_image() {
        finish();
    }

    @OnClick(R.id.iv_go)
    void iv_go() {
        routeplanToNavi();
    }

    @Bind(R.id.tv_toolbar)
    TextView tv_toolbar;
    @Bind(R.id.tv_gas_location)
    TextView tv_gas_location;
    @Bind(R.id.tv_date_time)
    TextView tv_date_time;
    @Bind(R.id.iv_quick_code)
    ImageView iv_quick_code;
    @Bind(R.id.iv_go)
    ImageView iv_go;
    private MapInfo startPoint, targetPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_success);
        ButterKnife.bind(this);
        this.startPoint = getIntent().getParcelableExtra(Config.KEY_START_LATING);
        this.targetPoint = getIntent().getParcelableExtra(Config.KEY_END_LATING);
        iv_go.setVisibility(startPoint == null ? View.INVISIBLE : View.VISIBLE);
        String filePath = getFileRoot(this) + File.separator
                + "qr_" + System.currentTimeMillis() + ".jpg";
        String gasName = getIntent().getStringExtra(Config.KEY_GAS_NAME);
        tv_toolbar.setText(gasName);
        tv_gas_location.setText(gasName + "感谢您的预约");
        tv_date_time.setText(getIntent().getStringExtra(Config.KEY_DATE_TIME));
        if (QRCodeUtil.createQRImage(getIntent().getStringExtra(Config.KEY_DATE_JSON)
                , DensityUtil.dip2px(this, 300), DensityUtil.dip2px(this, 300), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), filePath)) {
            ImageLoader.getInstance().displayImage("file://" + filePath, iv_quick_code);
        }
    }

    //文件存储根目录
    private String getFileRoot(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }

        return context.getFilesDir().getAbsolutePath();
    }

    private void routeplanToNavi() {
        Log.d("开始策划");
        //起点
        final ProgressDialog dialog = ProgressDialogUtils.from(mContext)
                .setTitle("提示:")
                .setMessage("正在计算路径...")
                .setCanceledOnTouchOutside(false)
                .show();
        Toast.makeText(mContext, "正在计算路径...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, NaviActivity.class)
                .putExtra(Config.KEY_START_LATING, startPoint)
                .putExtra(Config.KEY_END_LATING, targetPoint
                ));
        dialog.dismiss();
    }
}
