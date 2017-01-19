package com.implementist.treantreading;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.RadioGroup;

import com.implementist.treantreading.frag.BookListFragment;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private BookListFragment bookListFragment;
    private RadioGroup rgTabMenu;

    @Override
    public void widgetOnClick(View v) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

    }

    @Override
    public void initParams(Bundle params) {
        //解析bundle内容或者设置是否旋转，沉浸，全屏
        isSetStatusBar = false;
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
        bookListFragment = new BookListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, bookListFragment).commit();
        rgTabMenu = $(R.id.tab_menu);
    }

    @Override
    public void setListener() {
        rgTabMenu.setOnCheckedChangeListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
