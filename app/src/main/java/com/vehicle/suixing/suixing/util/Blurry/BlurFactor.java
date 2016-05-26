package com.vehicle.suixing.suixing.util.Blurry;

import android.graphics.Color;

/**
 * Created by KiSoo on 2016/5/13.
 */
public class BlurFactor {

    public static final int DEFAULT_RADIUS = 25;
    public static final int DEFAULT_SAMPLING = 1;

    public int width;
    public int height;
    public int radius = DEFAULT_RADIUS;
    public int sampling = DEFAULT_SAMPLING;
    public int color = Color.TRANSPARENT;
}