package com.hogan.cheng.libaray.cache;

import android.content.Context;

import com.hogan.cheng.libaray.cache.disk.DiskCacheManager;
import com.hogan.cheng.libaray.cache.memory.LruCacheManager;

/**
 * Created by liucheng on 2017/11/7.
 */

public class CacheManager {
    private static LruCacheManager lruCacheManager;
    private static DiskCacheManager diskCacheManager;

    public static void initCacheDir(Context context, String cachePath) {
        lruCacheManager = LruCacheManager.getInstance(context);
        diskCacheManager = DiskCacheManager.getInstance(context, cachePath);
    }

    public static LruCacheManager getLruCacheManager() {
        return lruCacheManager;
    }

    public static DiskCacheManager getDiskCacheManager() {
        return diskCacheManager;
    }
}
