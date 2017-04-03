package com.implementist.ireading.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.implementist.ireading.MyApplication;
import com.implementist.ireading.R;
import com.implementist.ireading.Utils;
import com.implementist.ireading.fragment.BookListFragment;
import com.implementist.ireading.fragment.FavoritesFragment;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */

public class MainActivity extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {

    private BookListFragment bookListFragment;
    private FavoritesFragment favoritesFragment;
    private BottomNavigationView navigation;
    private TextView tvTitle;


    @Override
    public void initParams(Bundle params) {
        //解析bundle内容或者设置是否旋转，沉浸，全屏
        //isSetStatusBar = true;
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
        favoritesFragment = new FavoritesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, bookListFragment)
                .commit();

        navigation = $(R.id.navigation);
        tvTitle = $(R.id.tvTitle);

        //取消BottomNavigationView各item切换时的位移动效
        Utils.disableShiftMode(navigation);
    }

    @Override
    public void setListener() {
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetOnClick(View v) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int[] slideAnimation;

        switch (item.getItemId()) {
            case R.id.navigation_bookList:
                tvTitle.setText(getResources().getString(R.string.book_list));

                //TODO: The following method will call onCreate again.Try any other implement method.
                if (null == bookListFragment)
                    bookListFragment = new BookListFragment();

                //通过简直映射得到动画数组
                slideAnimation = Utils.getSlideAnimationByCalculate(
                        MyApplication.FRAGMENT_MAP.get("BookList"),
                        MyApplication.FRAGMENT_MAP.get(MyApplication.lastFragment)
                );

                //带动画替换Fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(slideAnimation[0], slideAnimation[1])
                        .replace(R.id.main_content, bookListFragment)
                        .commit();

                //更改当前Fragment全局变量
                MyApplication.lastFragment = "BookList";

                return true;

            case R.id.navigation_favorites:
                tvTitle.setText(getResources().getString(R.string.favorites));
                if (null == favoritesFragment)
                    favoritesFragment = new FavoritesFragment();

                slideAnimation = Utils.getSlideAnimationByCalculate(
                        MyApplication.FRAGMENT_MAP.get("Favorites"),
                        MyApplication.FRAGMENT_MAP.get(MyApplication.lastFragment)
                );

                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(slideAnimation[0], slideAnimation[1])
                        .replace(R.id.main_content, favoritesFragment)
                        .commit();

                MyApplication.lastFragment = "Favorites";

                return true;
            case R.id.navigation_garden:
                tvTitle.setText(getResources().getString(R.string.garden));
                return true;
            case R.id.navigation_me:
                tvTitle.setText(getResources().getString(R.string.me));
                return true;
        }
        return false;
    }
}
