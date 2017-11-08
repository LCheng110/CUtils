package com.hogan.cheng.cutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hogan.cheng.libaray.cache.CacheManager;
import com.hogan.cheng.libaray.cache.disk.DiskCacheManager;
import com.hogan.cheng.libaray.cache.memory.LruCacheManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "sss";
    private static final String TEST = "test";
    Button button1, button2, button3;
    LruCacheManager lruCacheManager;
    DiskCacheManager diskCacheManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        lruCacheManager = CacheManager.getLruCacheManager();
        diskCacheManager = CacheManager.getDiskCacheManager();
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                Log.i(TAG, "lruCacheManager.getCacheData: " + lruCacheManager.getCacheData(TEST));
                Log.i(TAG, "diskCacheManager.readCacheString: " + diskCacheManager.readCacheString(TEST));
//                lruCacheManager.getCacheData("test");
//                diskCacheManager.readCacheString("test");
                break;
            case R.id.button2:
                lruCacheManager.setCacheData(TEST, "sssweqrqwrsssssś");
                diskCacheManager.writeCache(TEST, "áàaaaaś");
                break;
            case R.id.button3:
                Log.i(TAG, "diskCacheManager.removeCache: " + diskCacheManager.removeCache(TEST));
                break;
        }
    }
}
