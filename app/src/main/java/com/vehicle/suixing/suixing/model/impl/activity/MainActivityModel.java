package com.vehicle.suixing.suixing.model.impl.activity;

import android.content.Context;

import com.vehicle.suixing.suixing.app.SuixingApp;
import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.callback.BmobListener;
import com.vehicle.suixing.suixing.callback.UpdateList;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.model.activity.IMainActivityModel;
import com.vehicle.suixing.suixing.util.BmobUtils;
import com.vehicle.suixing.suixing.util.DbDao;
import com.vehicle.suixing.suixing.util.SaveUser;
import com.vehicle.suixing.suixing.util.UserSpUtils;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by KiSoo on 2016/4/20.
 */
public class MainActivityModel implements IMainActivityModel {
    @Override
    public void initUser(Context context,UpdateList listener, final BmobListener bmobListener) {

        User user = BmobUser.getCurrentUser(context, User.class);
        if (user != null) {
            /**
             * 更新信息
             * */
            List<String> userInfo = SaveUser.getUser(context);
            user.setUsername(userInfo.get(0));
            user.setPassword(userInfo.get(1));
            user.login(context, new SaveListener() {
                @Override
                public void onSuccess() {
                    /**
                     * 登录成功，更新好了
                     * */
                    bmobListener.onSuccess();
                }

                @Override
                public void onFailure(int i, String s) {
                    /**
                     * 登录失败，返回登录界面
                     * */
                    if (i == 101) {
                        /**
                         * 退出登录
                         * */
                    bmobListener.onFailure(i,s);

                    }
                }
            });
            SuixingApp.hasUser = true;
            Config.USERNAME = user.getUsername();
            SuixingApp.infos = DbDao.queryPart(context, Config.USERNAME);
            BmobUtils.updateList(context, listener);
            UserSpUtils.saveName(context, user.getName());
            UserSpUtils.saveMotto(context, user.getMotto());
            UserSpUtils.saveHead(context,user.getHead());

        } else {
            SuixingApp.hasUser = false;
        }
    }
}
