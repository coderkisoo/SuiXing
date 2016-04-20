package com.vehicle.suixing.suixing.model.impl.activity;

import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.bean.WeiZhang1.WeizhangDate;
import com.vehicle.suixing.suixing.callback.GetWeizhangInfo;
import com.vehicle.suixing.suixing.callback.SwitchDate;
import com.vehicle.suixing.suixing.model.activity.IPeccanyActivityModel;
import com.vehicle.suixing.suixing.util.JisuApiQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by KiSoo on 2016/4/20.
 */
public class PeccanyActivityModel implements IPeccanyActivityModel {
    /*开始查询*/
    @Override
    public void beginQuery(VehicleInformation info,GetWeizhangInfo callback) {
        JisuApiQuery query = new JisuApiQuery();
        query.query(info.getNum(), info.getFramenum(), info.getModel(), callback);
    }
    /*将违章信息根据天气来分离开*/
    @Override
    public void switchDate(List<WeizhangDate> infos,SwitchDate callback) {
        List<WeizhangDate> past = new ArrayList<>();
        List<WeizhangDate> now = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        if (null != infos) {
            for (WeizhangDate weizhangDate : infos) {
                String date = weizhangDate.getTime();
                int peccanyMonth = Integer.parseInt(date.substring(0, 4));
                int peccanyYear = Integer.parseInt(date.substring(5, 7));
                //年份小于今年，不是本月
                if (peccanyYear < year) {
                    past.add(weizhangDate);
                    continue;
                }
                //月份小于本月，不是本月
                if (peccanyMonth < month) {
                    past.add(weizhangDate);
                    continue;
                }
                //做完判断，添加到本月
                now.add(weizhangDate);
            }
            callback.getPast(past);
            callback.getNow(now);
        }
    }
}
