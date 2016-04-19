package com.vehicle.suixing.suixing.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.vehicle.suixing.suixing.app.SuixingApp;
import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.model.MainActivityView;
import com.vehicle.suixing.suixing.model.MeFragmentView;
import com.vehicle.suixing.suixing.ui.adapter.InfoAdapter;
import com.vehicle.suixing.suixing.util.BmobError;
import com.vehicle.suixing.suixing.util.SaveUser;
import com.vehicle.suixing.suixing.util.UserSpUtils;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by KiSoo on 2016/4/11.
 */
public class MeFragmentPresenter {
    private MeFragmentView view;
    private Context context;
    private static String TAG = MeFragmentPresenter.class.getName();
    private MainActivityView mainActivityView;
    public MeFragmentPresenter(Context context, MeFragmentView view, MainActivityView mainActivityView) {
        this.context = context;
        this.view = view;
        this.mainActivityView = mainActivityView;
    }

    public void getInfo() {
        view.setAdapter(new InfoAdapter(SuixingApp.infos, UserSpUtils.getUsers(context), view));
    }

    public void setHead(final String head) {
        view.setUpdate(true);
        //在此处先上传图片，然后再将其设置到后台表中，最后再将图片设置为头像
        Log.e(TAG,"正在上传图片"+head);
        final BmobFile file = new BmobFile(new File(head));
        file.upload(context, new UploadFileListener() {
            @Override
            public void onSuccess() {
                final User user = new User();
                user.setHead(file.getFileUrl(context));
                user.update(context, BmobUser.getCurrentUser(context, User.class).getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Log.e(TAG, "设置成功");
                        Toast.makeText(context, "设置成功", Toast.LENGTH_SHORT).show();
                        view.setUpdate(false);
                        User newuser = UserSpUtils.getUsers(context);
                        final String newHead = "file:///" + head;
                        newuser.setHead(newHead);
                        UserSpUtils.saveHead(context,file.getFileUrl(context));
                        view.setAdapter(new InfoAdapter(SuixingApp.infos, newuser, view));
                        mainActivityView.updateHead(newHead);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(context, BmobError.error(i), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "设置失败 " + BmobError.error(i));
                        view.setUpdate(false);
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e(TAG, "上传失败" + BmobError.error(i));
                view.setUpdate(false);
                Toast.makeText(context, BmobError.error(i), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onResume() {
        view.setAdapter(new InfoAdapter(SuixingApp.infos, UserSpUtils.getUsers(context), view));
        mainActivityView.resume();
    }

    public void refresh() {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo(SaveUser.isTel(Config.USERNAME)?"mobilePhoneNumber":"username",Config.USERNAME);
        query.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                User user = list.get(0);
                UserSpUtils.saveMotto(context,user.getMotto());
                UserSpUtils.saveName(context, user.getName());
                UserSpUtils.saveHead(context,user.getHead());
                onResume();
                view.setUpdate(false);
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(context,BmobError.error(i),Toast.LENGTH_SHORT).show();
                view.setUpdate(false);
            }
        });
    }
}
