package cc.hqu.sends.myzhihudaily;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class MyZhiHuDailyApplication extends Application {
    private static RequestQueue requestQueue;
    private static Context applicationContext;
    //初始化ImageLoader配置

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

        requestQueue = Volley.newRequestQueue(applicationContext);
    }
}
