package com.vehicle.suixing.suixing.callback;

import java.util.List;

/**
 * Created by KiSoo on 2016/4/20.
 */
public interface BmobListener<T> {
    void onSuccess();
    void onFailure(int i, String s);
    void onSuccess(List<T> list);
}
