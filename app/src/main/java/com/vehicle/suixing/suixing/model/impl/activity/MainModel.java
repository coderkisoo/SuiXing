package com.vehicle.suixing.suixing.model.impl.activity;

import android.content.Context;
import android.content.Intent;

import com.vehicle.suixing.suixing.app.SuixingApp;
import com.vehicle.suixing.suixing.bean.BmobBean.User;
import com.vehicle.suixing.suixing.callback.BmobListener;
import com.vehicle.suixing.suixing.callback.UpdateList;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.model.activity.IMainModel;
import com.vehicle.suixing.suixing.service.VehicleService;
import com.vehicle.suixing.suixing.util.RegisterUtils.BmobUtils;
import com.vehicle.suixing.suixing.util.dataBase.DbDao;
import com.vehicle.suixing.suixing.util.RegisterUtils.SaveUser;
import com.vehicle.suixing.suixing.util.RegisterUtils.SpUtils;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by KiSoo on 2016/4/20.
 */
public class MainModel implements IMainModel {
    @Override
    public void initUser(Context context, UpdateList listener, final BmobListener bmobListener) {

        User user = BmobUser.getCurrentUser(context, User.class);

        if (user != null) {
            /**
             * 更新信息
             * */
            Config.USERNAME = BmobUser.getCurrentUser(context, User.class).getMobilePhoneNumber();
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
                        bmobListener.onFailure(i, s);

                    }
                }
            });
            SuixingApp.hasUser = true;
            SuixingApp.infos = DbDao.queryPart(context, Config.USERNAME);
            context.startService(new Intent(context, VehicleService.class));
            BmobUtils.updateList(context, listener);
            SpUtils.saveName(context, user.getName());
            SpUtils.saveMotto(context, user.getMotto());
            SpUtils.saveHead(context, user.getHead());

        } else {
            SuixingApp.hasUser = false;
        }
    }

}
