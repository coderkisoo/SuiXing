package com.vehicle.suixing.suixing.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.vehicle.suixing.suixing.bean.BmobBean.VehicleImage;
import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.model.AddSuccessActivityView;
import com.vehicle.suixing.suixing.util.DbDao;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by KiSoo on 2016/4/4.
 */
public class AddSuccessActivityPresenter {
    private String TAG = AddSuccessActivityPresenter.class.getName();
    private AddSuccessActivityView view;
    private Context context;
    private BmobFile img;


    public AddSuccessActivityPresenter(AddSuccessActivityView view, Context context) {
        this.view = view;
        this.context = context;
    }


    public void addSuccess() {
        /**
         * 确认添加,将图片缓存到本地
         * */
        Log.e(TAG, img.getFilename() + "\n" + img.getFileUrl(context) + "\n" + img.getUrl());
        DbDao.add(context, Config.USERNAME, view.getInformation(), img.getFileUrl(context));
        view.finish();
    }

    public void startDownLoad() {
        view.launch();
        final VehicleInformation information = view.getInformation();
        String name = information.getName();
        BmobQuery<VehicleImage> query = new BmobQuery<VehicleImage>();
        query.addWhereEqualTo("vehicleId", name);
        query.setLimit(1);
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ONLY);
        query.findObjects(context, new FindListener<VehicleImage>() {
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
                view.displayImg(img.getFileUrl(context));
                Log.e(TAG, img.getFileUrl(context));
                /**
                 * 此处得到url
                 * */
                Log.e(TAG, img.getUrl());
                Log.e(TAG, img.getFilename());
//                startActivity(new Intent(AddSuccessActivity.this,TestActivity.class).putExtra("0",img.getUrl()));
                view.launched();
                view.tvAddSuccess();
                view.setName(information.getName());
                view.setNumber(information.getNum());
            }

            @Override
            public void onError(int i, String s) {
                /**
                 * 查询失败
                 * */
                Toast.makeText(context, "你的网络似乎有些问题哦", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "出错原因：" + s + "错误代码：" + i);
                view.launchFailed();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            sleep(2000);
                            view.finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });

    }
}
