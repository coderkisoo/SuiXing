package com.vehicle.suixing.suixing.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.vehicle.suixing.suixing.app.SuixingApp;
import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.callback.BmobListener;
import com.vehicle.suixing.suixing.callback.BmobListenerWithProgress;
import com.vehicle.suixing.suixing.model.fragment.IMeFragmentModel;
import com.vehicle.suixing.suixing.model.impl.fragment.MeFragmentModel;
import com.vehicle.suixing.suixing.ui.adapter.InfoAdapter;
import com.vehicle.suixing.suixing.util.BmobError;
import com.vehicle.suixing.suixing.util.UserSpUtils;
import com.vehicle.suixing.suixing.view.activity.MainActivityView;
import com.vehicle.suixing.suixing.view.fragment.MeFragmentView;

/**
 * Created by KiSoo on 2016/4/11.
 */
public class MeFragmentPresenter {
    private MeFragmentView view;
    private Context context;
    private IMeFragmentModel model;
    private static String TAG = MeFragmentPresenter.class.getName();
    private MainActivityView mainActivityView;
    public MeFragmentPresenter(Context context, MeFragmentView view, MainActivityView mainActivityView) {
        this.context = context;
        this.view = view;
        this.mainActivityView = mainActivityView;
        this.model = new MeFragmentModel();
    }


    public void setHead(final String head) {
        //在此处先上传图片，然后再将其设置到后台表中，最后再将图片设置为头像
        view.setUpdate(true);
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("提示:");
        dialog.setMessage("正在上传中 : 0%");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Log.e(TAG,"正在上传图片"+head);
        model.setHead(context, head, new BmobListenerWithProgress() {
            @Override
            public void onProgress(int i) {
                dialog.setMessage("正在上传中 : " + i + "%");
            }

            @Override
            public void onSuccess() {
                User newuser = UserSpUtils.getUsers(context);
                final String newHead = "file:///" + head;
                newuser.setHead(newHead);
                view.setAdapter(new InfoAdapter(SuixingApp.infos, newuser, view));
                mainActivityView.updateHead(newHead);
                Toast.makeText(context, "设置成功", Toast.LENGTH_SHORT).show();
                view.setUpdate(false);
                dialog.dismiss();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, BmobError.error(i), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "设置失败 " + BmobError.error(i));
                view.setUpdate(false);
                dialog.dismiss();
            }
        });

    }
    /*更新信息*/
    public void onResume() {
        view.setAdapter(new InfoAdapter(SuixingApp.infos, UserSpUtils.getUsers(context), view));
        mainActivityView.resume();
    }

    public void refresh() {
        model.updateUser(context, new BmobListener() {
            @Override
            public void onSuccess() {
                onResume();
                view.setUpdate(false);
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, BmobError.error(i), Toast.LENGTH_SHORT).show();
                view.setUpdate(false);
            }

        });
    }
}
