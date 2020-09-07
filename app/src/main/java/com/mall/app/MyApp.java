package com.mall.app;

import android.app.Application;
import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;
import com.mall.common.MyFileNameGenerator;


//import leakcanary.LeakCanary;

public class MyApp extends Application {

    public static Context app;

    private HttpProxyCacheServer proxy;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        /*LeakCanary.Config config = LeakCanary.getConfig().newBuilder()
                .retainedVisibleThreshold(3)
                .computeRetainedHeapSize(false)
                .build();
        LeakCanary.setConfig(config);*/
    }

    public static HttpProxyCacheServer getProxy(Context context){
        MyApp myApp = (MyApp) context.getApplicationContext();
        return myApp.proxy == null ? (myApp.proxy = myApp.newProxy()) : myApp.proxy;
    }

    private HttpProxyCacheServer newProxy(){
        return new HttpProxyCacheServer.Builder(this)
                .maxCacheSize(1024*1024*1024)
                .maxCacheFilesCount(30)
                .fileNameGenerator(new MyFileNameGenerator())
                .build();
    }
}
