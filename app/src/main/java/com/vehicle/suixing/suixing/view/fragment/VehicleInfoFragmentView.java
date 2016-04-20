package com.vehicle.suixing.suixing.view.fragment;

import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.ui.adapter.MyPagerAdapter;

/**
 * Created by KiSoo on 2016/4/4.
 */
public interface VehicleInfoFragmentView {
    /**
     * 汽车信息的model
     * */
    void setAdapter(MyPagerAdapter adapter);

    void initInfo(VehicleInformation vehicleInformation);
}
