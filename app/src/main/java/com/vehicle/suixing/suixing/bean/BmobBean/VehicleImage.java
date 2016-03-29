package com.vehicle.suixing.suixing.bean.BmobBean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by KiSoo on 2016/3/22.
 */
public class VehicleImage extends BmobObject {
    String vehicleId;
    BmobFile vehicleImg;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public BmobFile getVehicleImg() {
        return vehicleImg;
    }

    public void setVehicleImg(BmobFile vehicleImg) {
        this.vehicleImg = vehicleImg;
    }
}
