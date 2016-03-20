package com.vehicle.suixing.suixing.ui.adapter;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by KiSoo on 2016/3/20.
 */
public class MyPageTransFormer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.5f;
    private String TAG = "TransFromer";
    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

      /*  if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setScaleX(0.5f);
            view.setScaleY(0.5f);
            Log.e(TAG,"position < -1");
        } else */
        float m = Math.abs(position);
        if(position>1){
            view.setScaleX(0.5f);
            view.setScaleY(0.5f);
        }
        else if (position <= 0) { // [-1,0]

            // Use the default slide transition when moving to the left page
            view.setTranslationX(m);
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            view.setScaleY(1 - position);
            view.setScaleX(1 - position);
            // Counteract the default slide transition
            view.setTranslationX(pageWidth * -position);

            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setTranslationX(m);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setScaleX(0.5f);
            view.setScaleY(0.5f);

        }
    }
}
