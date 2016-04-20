package com.vehicle.suixing.suixing.callback;

import java.util.List;

/**
 * Created by KiSoo on 2016/4/20.
 */
public interface BmobListenerWithList<T> extends BmobListener {
    void onSuccess(List<T> list);

}
