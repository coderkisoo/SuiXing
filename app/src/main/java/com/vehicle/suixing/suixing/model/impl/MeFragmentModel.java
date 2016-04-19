package com.vehicle.suixing.suixing.model.impl;

import android.content.Context;

import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.callback.BmobListener;
import com.vehicle.suixing.suixing.callback.UrlGeter;
import com.vehicle.suixing.suixing.common.Config;
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
 * Created by KiSoo on 2016/4/20.
 */
public class MeFragmentModel implements com.vehicle.suixing.suixing.model.IMeFragmentModel {
    private String TAG = this.getClass().getName();

    @Override
    public void setHead(final Context context, final String head, final BmobListener listener) {
        final BmobFile file = new BmobFile(new File(head));
        file.upload(context, new UploadFileListener() {
            @Override
            public void onSuccess() {
                final User user = new User();
                user.setHead(file.getFileUrl(context));
                user.update(context, BmobUser.getCurrentUser(context, User.class).getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        listener.onSuccess();
                        UserSpUtils.saveHead(context, file.getFileUrl(context));
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        listener.onFailure(i, s);
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                listener.onFailure(i, s);
            }
        });
    }

    @Override
    public void updateUser(final Context context, final BmobListener<User> listener) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo(SaveUser.isTel(Config.USERNAME)?"mobilePhoneNumber":"username",Config.USERNAME);
        query.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                User user = list.get(0);
                UserSpUtils.saveMotto(context, user.getMotto());
                UserSpUtils.saveName(context, user.getName());
                UserSpUtils.saveHead(context, user.getHead());
                listener.onSuccess();

            }

            @Override
            public void onError(int i, String s) {
               listener.onFailure(i,s);
            }
        });
    }
}
