package com.vehicle.suixing.suixing.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.BmobBean.VehicleImage;
import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.ui.BaseSlidingActivity;
import com.vehicle.suixing.suixing.util.DbDao;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by KiSoo on 2016/3/21.
 */
public class AddSuccessActivity extends BaseSlidingActivity {

    private BmobFile img;
    private VehicleInformation information;


    @Bind(R.id.rl_islaunch)
    RelativeLayout rl_islaunch;
    @Bind(R.id.iv_vehicle_img)
    ImageView iv_vehicle_img;
    @Bind(R.id.pb_islaunch)
    ProgressBar pb_islaunch;
    @Bind(R.id.tv_vehicle_information_name)
    TextView tv_vehicle_information_name;
    @Bind(R.id.tv_vehicle_information_number)
    TextView tv_vehicle_information_number;
    @Bind(R.id.tv_add_success)
    TextView tv_add_success;

    @OnClick(R.id.tv_add_success)
    void tv_add_success() {
        /**
         * 确认添加,将图片缓存到本地
         * */
        Log.e(TAG, img.getFilename() + "\n" + img.getFileUrl(AddSuccessActivity.this) + "\n" + img.getUrl());
        DbDao.add(AddSuccessActivity.this, "123", information, img.getFileUrl(AddSuccessActivity.this));
        finish();
    }

    @OnClick(R.id.iv_toolbar_left_image)
    void iv_toolbar_left_image() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_success);
        ButterKnife.bind(this);
        information = (VehicleInformation) getIntent().getSerializableExtra("INFO");
        rl_islaunch.setVisibility(View.VISIBLE);
        tv_add_success.setVisibility(View.GONE);
        getImage(information);
    }

    private void getImage(final VehicleInformation information) {
        String name = information.getName();
        BmobQuery<VehicleImage> query = new BmobQuery<VehicleImage>();
        query.addWhereEqualTo("vehicleId", name);
        query.setLimit(1);
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ONLY);
        query.findObjects(this, new FindListener<VehicleImage>() {
            @Override
            public void onSuccess(List<VehicleImage> list) {
                /**
                 * 查询成功，将图片赋值
                 * */
                img = list.get(0).getVehicleImg();
                //picasso的图片缓存
//                Picasso.with(AddSuccessActivity.this)
//                        .load(img.getFileUrl(AddSuccessActivity.this))
//                        .into(iv_vehicle_img);
                //Bmob的图片缓存
//                img.loadImage(AddSuccessActivity.this, iv_vehicle_img);
                ImageLoader
                        .getInstance()
                        .displayImage(img.getFileUrl(AddSuccessActivity.this), iv_vehicle_img);
                Log.e(TAG, img.getFileUrl(AddSuccessActivity.this));
                /**
                 * 此处得到url
                 * */
                Log.e(TAG, img.getUrl());
                Log.e(TAG,img.getFilename());
//                startActivity(new Intent(AddSuccessActivity.this,TestActivity.class).putExtra("0",img.getUrl()));
                rl_islaunch.setVisibility(View.GONE);
                tv_add_success.setVisibility(View.VISIBLE);
                tv_vehicle_information_name.setText(information.getName());
                tv_vehicle_information_number.setText(information.getNum());
            }

            @Override
            public void onError(int i, String s) {
                /**
                 * 查询失败
                 * */
                Toast.makeText(AddSuccessActivity.this, "你的网络似乎有些问题哦", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"出错原因："+s+"错误代码："+i);
                pb_islaunch.setVisibility(View.GONE);
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            sleep(2000);
                            finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }
}
