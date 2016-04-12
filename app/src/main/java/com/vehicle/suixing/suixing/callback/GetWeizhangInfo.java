package com.vehicle.suixing.suixing.callback;

import com.vehicle.suixing.suixing.bean.WeiZhang1.WeizhangDate;

import java.util.List;

/**
 * Created by KiSoo on 2016/4/6.
 */
public interface GetWeizhangInfo {
//    void geted(WeizhangResponseHistoryJson json);

    void requestSuccess(List<WeizhangDate> list);
    void requestDefault(String error);

    void showText(String msg);
}
