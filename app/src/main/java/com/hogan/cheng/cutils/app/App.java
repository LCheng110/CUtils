package com.hogan.cheng.cutils.app;

import android.app.Application;

import com.hogan.cheng.libaray.cache.CacheManager;

/**
 * Created by liucheng on 2017/11/8.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CacheManager.initCacheDir(this, "testCache");
    }
}
