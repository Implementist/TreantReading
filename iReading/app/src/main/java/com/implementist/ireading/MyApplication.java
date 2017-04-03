package com.implementist.ireading;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */

public class MyApplication extends Application {
    // 建立请求队列
    private static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.start();
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
