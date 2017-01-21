package com.implementist.treantreading;

import android.content.Context;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */

public interface IPullHeaderFactory {

    PullHeader made(Context context);

    boolean isPinContent();

}
