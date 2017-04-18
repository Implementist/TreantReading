package com.implementist.ireading;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.callback.IFooterCallBack;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */

public class RefreshViewFooter extends LinearLayout implements IFooterCallBack {
    private Context mContext;

    private View lytFooter;
    private View mProgressBar;
    private TextView tvLoadState;
    private TextView tvReleaseToLoadMore;
    private boolean isShowing = true;

    public RefreshViewFooter(Context context) {
        super(context);
        initView(context);
    }

    public RefreshViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        ViewGroup moreView = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.rfv_footer, this);
        moreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        lytFooter = moreView.findViewById(R.id.lytFooter);
        mProgressBar = moreView.findViewById(R.id.rfv_footer_progressbar);
        tvLoadState = (TextView) moreView.findViewById(R.id.tvLoadState);
        tvReleaseToLoadMore = (TextView) moreView.findViewById(R.id.tvReleaseToLoadMore);
    }

    @Override
    public void callWhenNotAutoLoadMore(final XRefreshView xRefreshView) {
    }

    @Override
    public void onStateReady() {
        tvLoadState.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        tvReleaseToLoadMore.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStateRefreshing() {
        tvLoadState.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        tvReleaseToLoadMore.setVisibility(View.GONE);
        show(true);
    }

    @Override
    public void onReleaseToLoadMore() {
        tvLoadState.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        tvReleaseToLoadMore.setText(R.string.release_to_load_more);
        tvReleaseToLoadMore.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStateFinish(boolean hideFooter) {
        if (hideFooter) {
            tvLoadState.setText(R.string.loading_complete);
        } else {
            //处理数据加载失败时ui显示的逻辑，也可以不处理，看自己的需求
            tvLoadState.setText(R.string.loading_failed);
        }
        tvLoadState.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        tvReleaseToLoadMore.setVisibility(View.GONE);
    }

    @Override
    public void onStateComplete() {
        tvLoadState.setText(R.string.every_item_has_been_loaded);
        tvLoadState.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        tvReleaseToLoadMore.setVisibility(View.GONE);
    }

    @Override
    public void show(final boolean isShowing) {
        if (isShowing == this.isShowing) {
            return;
        }
        this.isShowing = isShowing;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) lytFooter
                .getLayoutParams();
        lp.height = isShowing ? LayoutParams.WRAP_CONTENT : 0;
        lytFooter.setLayoutParams(lp);

    }

    @Override
    public boolean isShowing() {
        return isShowing;
    }

    @Override
    public int getFooterHeight() {
        return getMeasuredHeight();
    }
}
