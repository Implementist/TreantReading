package com.implementist.ireading;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.implementist.ireading.fragment.BookListFragment;

import java.util.HashMap;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */

public class MyApplication extends Application {
    public final static HashMap<String, Integer> FRAGMENT_MAP = new HashMap<String, Integer>() {
        {
            put("BookList", 0);
            put("Favorites", 1);
            put("Garden", 2);
            put("Me", 3);
        }
    };

    public static String lastFragment;

    // 建立请求队列
    private static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        lastFragment = "BookList";

        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
