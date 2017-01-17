package com.implementist.treantreading;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.implementist.treantreading.pref.AppPref;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 * Logo Splash
 */

public class LogoSplashActivity extends Activity {

    private Handler handler = new Handler();
    private Runnable runnable;
    private int timeCount = 3;

    final Intent intent = new Intent(this, MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        //注意，这里没有setContentView,单纯只是用来跳转到相应的Activity
        //目的是减少首屏渲染
        if (AppPref.isFirstRunning(this))
            handler.postDelayed(runnable, 0);//开始计时器

        /**
         * 计时器线程
         */
        runnable = new Runnable() {
            @Override
            public void run() {
                if (timeCount > 0) {
                    timeCount--;
                    handler.postDelayed(this, 1000);
                } else {
                    startActivity(intent);
                    finish();
                    //停止计时器
                    handler.removeCallbacks(runnable);
                }
            }
        };
    }
}
