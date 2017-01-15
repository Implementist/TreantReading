package com.implementist.treantreading;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */

public class MainActivity extends BaseActivity {

    private Button btn;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                //用简化了的toast和startActivity
                showToast("Test");
                //startActivity(MainActivity.class);
                //Log.i("Test", "Test Message");
                break;
        }
    }

    @Override
    public void initParams(Bundle params) {
        //解析bundle内容或者设置是否旋转，沉浸，全屏
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    /**
     * [初始化绑定]
     */
    @Override
    public void initView(View view) {
        btn = $(R.id.button);
    }

    @Override
    public void setListener() {
        btn.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
