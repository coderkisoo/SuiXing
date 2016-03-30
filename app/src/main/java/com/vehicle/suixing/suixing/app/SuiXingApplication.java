package com.vehicle.suixing.suixing.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.vehicle.suixing.suixing.common.Config;

import java.io.File;
import java.util.ArrayList;

import cn.bmob.v3.Bmob;

/**
 * Created by KiSoo on 2016/3/24.
 */
public class SuiXingApplication extends Application {
    private static ArrayList<Activity> activities;
    private static String TAG = SuiXingApplication.class.getName();
    public static boolean hasUser = false;

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * bmob的初始化
         * */
        Bmob.initialize(this, Config.BMOBID);
        /**
         * imageloader的初始化
         * */
        initImageLoader(getApplicationContext());
        activities = new ArrayList<>();
    }

    public synchronized static void addActivity(Activity activity) {
        activities.add(activity);
        Log.e(TAG, activity.toString() + "已经被添加");
    }

    public synchronized static void clearAll() {
        int size = activities.size();
        for (int i = size; i > 0; i--) {
            activities.get(i - 1).finish();
        }
        if (activities.size() == 0)
            activities.clear();
    }

    public synchronized static void removeActivity(Activity activity) {
        activities.remove(activity);
        Log.e(TAG, activity.toString() + "已经被移除");
    }

    private void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getCacheDirectory(context);
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory().cacheOnDisc().build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .defaultDisplayImageOptions(defaultOptions)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCache(new UnlimitedDiscCache(cacheDir))
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .diskCacheFileCount(300)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
    }
}
