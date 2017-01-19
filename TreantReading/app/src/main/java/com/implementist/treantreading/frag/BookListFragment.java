package com.implementist.treantreading.frag;


import android.content.Context;
import android.view.View;

import com.implementist.treantreading.R;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 * Book List Fragment
 */
public class BookListFragment extends BaseFragment {
    public BookListFragment() {
        // Required empty public constructor
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_book_list;
    }

    @Override
    public void initView(View view) {
        //btn = $(R.id.button);
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
}
