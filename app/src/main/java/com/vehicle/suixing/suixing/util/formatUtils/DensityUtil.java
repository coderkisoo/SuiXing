package com.vehicle.suixing.suixing.util.formatUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by KiSoo on 2016/1/30.
 */
public class DensityUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    public static int getChineseCount(String str) {
        String regEx = "[\\u4e00-\\u9fa5]"; // unicode编码，判断是否为汉字
        int count = 0;
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                count = count + 1;
            }
        }
        return count;
    }
    public static int getStatusBarHeight(Context context) {
            try {
                Class c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (int) field.get(o);
                return context.getResources().getDimensionPixelOffset(x);

            } catch (Exception e) {
                e.printStackTrace();
            }
        return 1;
    }

    public static int getDisplayWidth(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }


}
