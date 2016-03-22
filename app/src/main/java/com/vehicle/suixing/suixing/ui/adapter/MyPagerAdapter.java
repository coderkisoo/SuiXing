package com.vehicle.suixing.suixing.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vehicle.suixing.suixing.bean.VehicleInformation;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by KiSoo on 2016/3/20.
 */
public class MyPagerAdapter extends PagerAdapter {
    private List<VehicleInformation> info;
    private Context context;
    @Override
    public int getCount() {
        return info.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(context);
        BmobFile bmobFile = new BmobFile();
        bmobFile.setUrl(info.get(position).getUrl());
        bmobFile.loadImage(context,imageView);
        container.addView(imageView, position);
        return imageView;

    }
    public void itemClick(){

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((ImageView)object);
    }

    public MyPagerAdapter(Context context, List<VehicleInformation> info) {
        this.context = context;
        this.info = info;
    }
}
