package com.vehicle.suixing.suixing.util.Blurry;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by KiSoo on 2016/5/13.
 */
public class BlurTask {

    public interface Callback {

        void done(BitmapDrawable drawable);
    }

    private Resources res;
    private WeakReference<Context> contextWeakRef;
    private BlurFactor factor;
    private Bitmap capture;
    private Callback callback;
    private static ExecutorService THREAD_POOL = Executors.newCachedThreadPool();

    public BlurTask(View target, BlurFactor factor, Callback callback) {
        target.setDrawingCacheEnabled(true);
        this.res = target.getResources();
        this.factor = factor;
        this.callback = callback;

        target.destroyDrawingCache();
        target.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        capture = target.getDrawingCache();
        contextWeakRef = new WeakReference<>(target.getContext());
    }

    public void execute() {
        THREAD_POOL.execute(new Runnable() {
            @Override public void run() {
                Context context = contextWeakRef.get();
                final BitmapDrawable bitmapDrawable =
                        new BitmapDrawable(res, Blur.of(context, capture, factor));

                if (callback != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override public void run() {
                            callback.done(bitmapDrawable);
                        }
                    });
                }
            }
        });
    }
}