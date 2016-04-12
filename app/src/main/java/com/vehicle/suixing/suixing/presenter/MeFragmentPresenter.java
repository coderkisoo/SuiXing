package com.vehicle.suixing.suixing.presenter;

import android.content.Context;

import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.model.MeFragmentView;
import com.vehicle.suixing.suixing.ui.adapter.InfoAdapter;
import com.vehicle.suixing.suixing.util.DbDao;
import com.vehicle.suixing.suixing.util.UserSpUtils;

import java.util.List;

/**
 * Created by KiSoo on 2016/4/11.
 */
public class MeFragmentPresenter {
    private MeFragmentView view;
    private Context context;

    public MeFragmentPresenter(Context context, MeFragmentView view) {
        this.context = context;
        this.view = view;
    }
    public void getInfo(){
        List<VehicleInformation> info  = DbDao.queryPart(context, Config.USERNAME);
        view.setAdapter(new InfoAdapter(info, UserSpUtils.getUsers(context)));
    }

}
