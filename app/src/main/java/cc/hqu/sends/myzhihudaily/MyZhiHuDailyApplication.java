package cc.hqu.sends.myzhihudaily;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by SHeng_Lin on 2016/3/12.
 */
public class MyZhiHuDailyApplication extends Application {
    private static RequestQueue requestQueue;
    private static Context applicationContext;
    //初始化ImageLoader配置
    private static void initImageLoader(Context context) {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .denyCacheImageMultipleSizesInMemory()
                .build();
        ImageLoader.getInstance().init(configuration);
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public static Context getContext() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        initImageLoader(applicationContext);
        requestQueue = Volley.newRequestQueue(applicationContext);
    }
}
