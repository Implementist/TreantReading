package com.implementist.treantreading.preference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 * App Launch Preference
 */
public class AppPref {

    private static final String KEY_IS_FIRST_RUNNING = "is_first_running";

    private static SharedPreferences getPreference(Context context) {
        return context.getApplicationContext()
                .getSharedPreferences("com.implementist.treantreading.preference.xml", Context.MODE_PRIVATE);
    }

    public static void setAlreadyRun(Context context) {
        getPreference(context).edit().putBoolean(KEY_IS_FIRST_RUNNING, false).apply();
    }

    //判断是否初次运行
    public static boolean isFirstRunning(Context context) {
        return getPreference(context).getBoolean(KEY_IS_FIRST_RUNNING, true);
    }
}
