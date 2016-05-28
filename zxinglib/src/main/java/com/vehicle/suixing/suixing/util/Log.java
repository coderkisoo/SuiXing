package com.vehicle.suixing.suixing.util;


/**
 * Created by KiSoo on 2016/5/26.
 */
public class Log {
    private static boolean debug = true;
    private static String TAG = "LogUtils";

    public static void e(String tag, String msg) {
        if (debug)
            android.util.Log.e(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (debug)
            android.util.Log.d(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (debug)
            android.util.Log.v(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (debug)
            android.util.Log.i(tag, msg);
    }

    public static void e(String tag, String s, Exception e) {
        if (debug)
            android.util.Log.e(tag, s, e);
    }

    public static void d(String info) {
        if (debug)
            android.util.Log.d(TAG,info);
    }
}
