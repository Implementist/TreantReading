package com.implementist.treantreading;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.implementist.treantreading.pref.AppPref;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */

public class LogoSplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //注意，这里没有setContentView,单纯只是用来跳转到相应的Activity
        //目的是减少首屏渲染
        if (AppPref.isFirstRunning(this)){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
