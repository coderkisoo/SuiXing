package com.vehicle.suixing.suixing.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by KiSoo on 2016/3/20.
 */
public class MyPagerAdapter extends PagerAdapter {
    int[] drawable = {};
    Context context;
    @Override
    public int getCount() {
        return drawable.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(drawable[position]);
        container.addView(imageView, position);
        return imageView;

    }
    public void itemClick(){

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((ImageView)object);
    }

    public MyPagerAdapter(Context context, int[] drawable) {
        this.context = context;
        this.drawable = drawable;
    }
}
