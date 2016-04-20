package com.vehicle.suixing.suixing.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.vehicle.suixing.suixing.bean.BmobBean.VehicleImage;
import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.callback.BmobListener;
import com.vehicle.suixing.suixing.callback.BmobListenerWithList;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.view.activity.AddSuccessActivityView;
import com.vehicle.suixing.suixing.model.impl.activity.AddSuccessActivityModel;
import com.vehicle.suixing.suixing.model.activity.IAddSuccessActivityModel;
import com.vehicle.suixing.suixing.util.BmobError;
import com.vehicle.suixing.suixing.util.DbDao;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by KiSoo on 2016/4/4.
 */
public class AddSuccessActivityPresenter {
    private String TAG = AddSuccessActivityPresenter.class.getName();
    private AddSuccessActivityView view;
    private IAddSuccessActivityModel model;
    private Context context;
    private BmobFile img;


    public AddSuccessActivityPresenter(AddSuccessActivityView view, Context context) {
        this.view = view;
        this.context = context;
        model = new AddSuccessActivityModel();
    }

    /**
     * 确认添加,将图片的url先存至网络，再缓存到本地
     * */
    public void addSuccess() {

        Log.e(TAG, img.getFilename() + "\n" + img.getFileUrl(context) + "\n" + img.getUrl());
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("提示:");
        dialog.setMessage("正在添加中...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        final VehicleInformation info = view.getInformation();
        info.setUsername(Config.USERNAME);
        info.setUrl(img.getFileUrl(context));
        model.saveOnInternet(context, info, new BmobListener() {
            @Override
            public void onSuccess() {
                view.showToast("添加成功");
                DbDao.add(context, info);
                dialog.dismiss();
                view.finish();
            }

            @Override
            public void onFailure(int i, String s) {
                dialog.dismiss();
                view.showToast(BmobError.error(i));
            }


        });

    }
    /**
     * 开始下载图片，图片存储在bmob后台上，请求来得到数据
     * */
    public void startDownLoad() {
        view.launch();
        model.startDownLoad(context, view.getInformation(), new BmobListenerWithList<VehicleImage>() {
            @Override
            public void onSuccess(List<VehicleImage> list) {
                /**
                 * 查询成功
                 * */
                img = list.get(0).getVehicleImg();
                view.displayImg(img.getFileUrl(context));
                view.launched();
                view.tvAddSuccess();
                view.setName(view.getInformation().getName());
                view.setNumber(view.getInformation().getNum());
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {
                Log.e(TAG, "出错原因：" + s + "错误代码：" + i);
                view.launchFailed();
                view.showToast(BmobError.error(i));
            }
        });
    }
}
