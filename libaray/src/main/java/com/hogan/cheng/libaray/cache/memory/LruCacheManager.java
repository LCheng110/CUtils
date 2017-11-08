package com.hogan.cheng.libaray.cache.memory;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by liucheng on 2017/11/8.
 * LruCache 缓存管理
 */

public class LruCacheManager {
    private static LruCacheManager lruCacheManager;
    private final LruCache<String, Object> memoryCache;
    private int memoryCacheSize; // Lru缓存大小 官方推荐是用当前app可用内存的八分之一

    private LruCacheManager(Context context) {
        // 获取APP内存大小，单位为兆(M)为了计算方便 乘以 1024 * 1024
        memoryCacheSize = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE))
                .getMemoryClass() * 1024 * 1024 / 8;
        memoryCache = new LruCache<String, Object>(memoryCacheSize) {
            @Override
            protected int sizeOf(String key, Object data) {
                if (data instanceof Bitmap) { // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                    return ((Bitmap) data).getByteCount();
                } else if (data instanceof String) {
                    return ((String) data).getBytes().length;
                }
                return 1;
            }
        };
    }

    public static LruCacheManager getInstance(Context context) {
        if (lruCacheManager == null) {
            lruCacheManager = new LruCacheManager(context);
        }
        return lruCacheManager;
    }

    public void setCacheData(String key, Object data) {
        memoryCache.put(key, data);
    }

    public Object getCacheData(String key) {
        return memoryCache.get(key);
    }

    public void setCacheSize(int cacheSize) {
        memoryCache.resize(cacheSize);
        memoryCacheSize = cacheSize;
    }

    public int getCacheSize() {
        return memoryCacheSize;
    }

}
