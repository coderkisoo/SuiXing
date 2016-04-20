package com.vehicle.suixing.suixing.model.impl.activity;

import android.content.Context;

import com.vehicle.suixing.suixing.bean.BmobBean.VehicleImage;
import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.callback.BmobListener;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.model.activity.IAddSuccessActivityModel;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by KiSoo on 2016/4/20.
 */
public class AddSuccessActivityModel implements IAddSuccessActivityModel {

    @Override
    public void saveOnInternet(final Context context,final VehicleInformation info, final BmobListener<VehicleImage> listener) {
        BmobQuery<VehicleInformation> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("username", Config.USERNAME);
        bmobQuery.addWhereEqualTo("num", info.getNum());
        bmobQuery.findObjects(context, new FindListener<VehicleInformation>() {
            @Override
            public void onSuccess(List<VehicleInformation> list) {
                if (list.size() == 0) {
                    /**
                     * 没有添加过，保存
                     * */
                    info.save(context, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            listener.onSuccess();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            listener.onFailure(i,s);
                        }
                    });
                } else {
                    /**
                     * 添加过，修改
                     * */
                    info.update(context, list.get(0).getObjectId(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            /**
                             * 修改成功
                             * */
                          listener.onSuccess();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            /**
                             * 修改失败
                             * */
                            listener.onFailure(i,s);
                        }
                    });
                }
            }
            @Override
            public void onError(int i, String s) {
                listener.onFailure(i, s);
            }
        });
    }

    @Override
    public void startDownLoad(Context context,final VehicleInformation information,final BmobListener listener) {
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
               listener.onSuccess(list);
            }

            @Override
            public void onError(int i, String s) {
                /**
                 * 查询失败
                 * */

              listener.onFailure(i,s);

            }
        });

    }
}
