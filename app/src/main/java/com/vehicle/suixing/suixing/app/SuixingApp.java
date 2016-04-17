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
import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.common.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;

/**
 * Created by KiSoo on 2016/3/24.
 */
public class SuixingApp extends Application {
    private static ArrayList<Activity> activities;
    private static String TAG = SuixingApp.class.getName();
    public static boolean hasUser = false;
    public static List<VehicleInformation> infos;

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * bmob的初始化
         * */
        Bmob.initialize(this, Config.BMOBID);
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation(this).save();
        // 启动推送服务
        BmobPush.startWork(this,Config.BMOBID);
        /**
         * imageloader的初始化
         * */
        initImageLoader(getApplicationContext());
        activities = new ArrayList<>();
        infos = new ArrayList<>();
        /**
         * 违章CLIENT的初始化
         * */
//        startService(new Intent(this,WeizhangIntentService.class)
//                .putExtra("appId", Config.WEIZHANG_APPID)
//                .putExtra("appKey", Config.WEIZHANG_KEY));
    }

    public synchronized static void addActivity(Activity activity) {
        activities.add(activity);
        Log.e(TAG, activity.toString() + "已经被添加");
    }

    public synchronized static void clearAll() {
//        int size = activities.size();
//        for (int i = size; i > 0; i--) {
//            activities.get(i - 1).finish();
//        }

//        Iterator<Activity> activityIterator = activities.iterator();
        while (activities.size()!=0){
            activities.get(0).finish();
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
