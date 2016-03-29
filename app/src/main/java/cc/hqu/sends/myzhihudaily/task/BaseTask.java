package cc.hqu.sends.myzhihudaily.task;

import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import cc.hqu.sends.myzhihudaily.MyZhiHuDailyApplication;


public class BaseTask {
    protected final RequestQueue mRequestQueue;
    protected final ImageLoader mImageLoader;
    protected final DisplayImageOptions mOptions;
    public BaseTask() {
        mRequestQueue = MyZhiHuDailyApplication.getRequestQueue();
        mImageLoader = ImageLoader.getInstance();
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }
}
