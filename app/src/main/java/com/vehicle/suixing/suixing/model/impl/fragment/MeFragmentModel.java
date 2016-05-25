package com.vehicle.suixing.suixing.model.impl.fragment;

import android.content.Context;
import android.util.Log;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.callback.BmobListener;
import com.vehicle.suixing.suixing.callback.BmobListenerWithProgress;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.model.fragment.IMeFragmentModel;
import com.vehicle.suixing.suixing.util.RegisterUtils.SaveUser;
import com.vehicle.suixing.suixing.util.RegisterUtils.SpUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by KiSoo on 2016/4/20.
 */
public class MeFragmentModel implements IMeFragmentModel {
    private String TAG = this.getClass().getName();

    @Override
    public void setHead(final Context context, final String head, final BmobListenerWithProgress listener) {
        Log.e(TAG, "开始上传图片");
        BmobProFile.getInstance(context).upload(head, new UploadListener() {
            @Override
            public void onSuccess(String s, String s1, final BmobFile file) {
                final User user = new User();
                user.setHead(file.getFileUrl(context));
                user.update(context, BmobUser.getCurrentUser(context, User.class).getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        listener.onSuccess();
                        SpUtils.saveHead(context, file.getFileUrl(context));
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        listener.onFailure(i, s);
                    }
                });
            }

            @Override
            public void onProgress(int i) {
                Log.e(TAG, "上传进度为:" + i);
                listener.onProgress(i);
            }

            @Override
            public void onError(int i, String s) {
                listener.onFailure(i, s);
            }
        });
    }

    @Override
    public void updateUser(final Context context, final BmobListener listener) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo(SaveUser.isTel(Config.USERNAME)?"mobilePhoneNumber":"username",Config.USERNAME);
        query.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                User user = list.get(0);
                SpUtils.saveMotto(context, user.getMotto());
                SpUtils.saveName(context, user.getName());
                SpUtils.saveHead(context, user.getHead());
                listener.onSuccess();

            }

            @Override
            public void onError(int i, String s) {
               listener.onFailure(i,s);
            }
        });
    }
}
