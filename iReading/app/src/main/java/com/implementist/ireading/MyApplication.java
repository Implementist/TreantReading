package com.implementist.ireading;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.liulishuo.filedownloader.FileDownloader;

import org.json.JSONArray;

import java.io.File;
import java.util.HashMap;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */

public class MyApplication extends Application {
    //Fragment切换表
    public final static HashMap<String, Integer> FRAGMENT_MAP = new HashMap<String, Integer>() {
        {
            put("BookList", 0);
            put("Favorites", 1);
            put("Garden", 2);
            put("Me", 3);
        }
    };

    //记录当前Fragment
    public static String lastFragment;

    // 建立请求队列
    private static RequestQueue requestQueue;

    //项目缓存文件夹，如以下方式书写便于检查格式
    public static final String EXTERNAL_CACHE_DIR = Environment.getExternalStorageDirectory().toString() +
            File.separator +
            "Android" +
            File.separator +
            "data" +
            File.separator +
            "com.implementist.ireading" +
            File.separator +
            "cache";

    public static JSONArray books = new JSONArray();

    public static int currentItemIndex;

    @Override
    public void onCreate() {
        super.onCreate();

        lastFragment = "BookList";

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        currentItemIndex = 0;

        //FileDownloader 初始化
        FileDownloader.init(getApplicationContext());

        //创建外部存储器目标路径
        createExternalCacheFolders(getApplicationContext());
    }

    /**
     * 获取Http请求队列
     *
     * @return Http请求队列
     */
    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    /**
     * 检查下载路径是否存在，不存在就创建
     *
     * @param context 应用程序上下文
     */
    private static void createExternalCacheFolders(Context context) {
        File file = new File(EXTERNAL_CACHE_DIR);
        if (!file.exists())
            context.getExternalCacheDir();
    }
}
