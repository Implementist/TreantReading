package com.implementist.treantreading.fragment;


import android.content.Context;
import android.view.View;

import com.implementist.treantreading.DefaultHeader;
import com.implementist.treantreading.R;
import com.implementist.treantreading.RefreshableView;
import com.implementist.treantreading.interfaces.RefreshEvent;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 * Book List Fragment
 */
public class BookListFragment extends BaseFragment implements RefreshEvent {

    RefreshableView refreshableView;
    BookListFragment fragment;
    RefreshEvent refreshEvent;

    public BookListFragment() {
        // Required empty public constructor
        fragment = this;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_book_list;
    }

    @Override
    public void initView(View view) {
        //btn = $(R.id.button);
        refreshableView = (RefreshableView) view.findViewById(R.id.rfvBookList);
        refreshableView.setPullHeader(new DefaultHeader());
        RefreshEvent refreshEvent = fragment;
        //下面的函数用于停止动画，用在更新加载完成后
        //refreshEvent.setRefreshing(false);
    }

    @Override
    public void setListener() {
        //btn.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetOnClick(View v) {
        //switch (v.getId()) {
        //case R.id.button:
        //startActivity(MainActivity.class);
        //Log.i("Test", "Test Message");
        //break;
        //}
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        refreshableView.setRefreshing(refreshing);
    }
}
