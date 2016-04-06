package com.vehicle.suixing.suixing.util;

import android.util.Log;

import com.vehicle.suixing.suixing.callback.GetWeizhangInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by KiSoo on 2016/4/6.
 */
public class JisuApiQuery {
    private static GetWeizhangInfo info;
    private static String TAG = "JisuApiQuery";
    public static void query(final String vehiclenum, final String engineNo, String frameNum, GetWeizhangInfo call) {
        info = call;
        Log.e(TAG, "vehiclenum" + vehiclenum + "engineNo" + engineNo + "frameNum" + frameNum);
        String firstLetter = vehiclenum.substring(0,1);
        String lsprefix = "";
        try {
            lsprefix = URLEncoder.encode(firstLetter,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}
