package com.hogan.cheng.libaray.cache.disk;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.hogan.cheng.libaray.utils.AppUtils;
import com.hogan.cheng.libaray.utils.EncryptUtil;
import com.hogan.cheng.libaray.utils.StorageUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by liucheng on 2017/11/7.
 * 磁盘缓存管理
 */

public class DiskCacheManager {

    private static DiskCacheManager diskCacheManager;
    private DiskLruCache mDiskLruCache;

    private DiskCacheManager(Context context, String cachePath) {
        try {
            File cacheDir = StorageUtils.getCacheDir(context, cachePath);
            if (!cacheDir.exists()) {
                if (cacheDir.mkdirs()) {
                    Log.i(AppUtils.TAG, "getCache: ");
                }
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, AppUtils.getAppVersion(context), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DiskCacheManager getInstance(Context context, String cachePath) {
        if (diskCacheManager == null) {
            diskCacheManager = new DiskCacheManager(context, cachePath);
        }
        return diskCacheManager;
    }

    /**
     * 缓存string
     *
     * @param key     string在缓存中的key 传对应string的唯一值
     * @param content string数据
     */
    public void writeCache(String key, String content) {
        writeCache(key, content.getBytes());
    }

    /**
     * 缓存bitmap
     *
     * @param key    bitmap在缓存中的key 传对应bitmap的唯一值
     * @param bitmap bitmap
     */
    public void writeCache(String key, Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        writeCache(key, outputStream.toByteArray());
    }

    /**
     * 缓存byte[]
     *
     * @param key     byte在缓存中的key 传对应byte的唯一值
     * @param content byte[]
     */
    public void writeCache(String key, byte[] content) {
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(EncryptUtil.md5(key));
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(0);
                InputStream is = new ByteArrayInputStream(content);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
                editor.commit();
            }
            mDiskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取缓存中对应key数据的输入流
     *
     * @param key 缓存数据对应key值
     * @return 返回值为null则缓存不存在
     */
    public InputStream readCacheInputStream(String key) {
        InputStream inputStream = null;
        try {
            DiskLruCache.Snapshot snapShot = mDiskLruCache.get(EncryptUtil.md5(key));
            if (snapShot != null) {
                inputStream = snapShot.getInputStream(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * 读取缓存中对应key数据的字符串
     *
     * @param key 缓存数据对应key值
     * @return 返回值为对应string
     */
    public String readCacheString(String key) {
        StringBuilder builder = new StringBuilder();
        String line;
        InputStream inputStream = readCacheInputStream(key);
        if (inputStream != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }

    /**
     * 删除对应key的缓存数据
     *
     * @param key 数据唯一对应的key
     * @return 是否删除成功
     */
    public boolean removeCache(String key) {
        try {
            return mDiskLruCache.remove(EncryptUtil.md5(key));
        } catch (IOException e) {
            Log.i("sss", "removeCache: " + e.getMessage());
            return false;
        }
    }

    /**
     * 清除全部缓存数据
     */
    public void removeAllCache() {
        try {
            mDiskLruCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取缓存数据的大小
     */
    public void getCacheSize() {
        mDiskLruCache.size();
    }
}
