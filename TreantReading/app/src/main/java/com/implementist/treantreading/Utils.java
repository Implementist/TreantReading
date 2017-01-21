package com.implementist.treantreading;

import android.content.Context;
import android.util.TypedValue;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */

class Utils {
    public static int dipToPix(Context context, float dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }
}
