package com.vehicle.suixing.suixing.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.vehicle.suixing.suixing.app.SuixingApp;

/**
 * Created by KiSoo on 2016/3/20.
 */
public class MyPagerAdapter extends PagerAdapter {
    private Context context;
    private String TAG = "MyPagerAdapter";
    @Override
    public int getCount() {
        return  SuixingApp.infos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(context);
        ImageLoader.getInstance()
                .displayImage( SuixingApp.infos.get(position).getUrl(), imageView);
        container.addView(imageView, 0);
        return imageView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((ImageView)object);
    }

    public MyPagerAdapter(Context context) {
        this.context = context;
    }
}
