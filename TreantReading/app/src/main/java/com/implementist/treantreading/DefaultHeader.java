package com.implementist.treantreading;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */

public class DefaultHeader implements PullHeader {

    private ImageView imageView;
    private TextView textView;
    private MaterialProgressDrawable materialProgressDrawable;
    private ImageView progressImageView;
    private RotateAnimation mFlipAnimation;
    private RotateAnimation mReverseFlipAnimation;
    private int mRotateAniTime = 150;
    private View headerView;
    private int backgroundColor = Color.parseColor("#989898");

    public void setBackgroundColor(int color) {
        backgroundColor = color;
        if (headerView != null) {
            headerView.setBackgroundColor(color);
        }
    }

    @Override
    public View createHeaderView(RefreshableView refreshableView) {
        Context context = refreshableView.getContext();
        headerView = LayoutInflater.from(context).inflate(R.layout.refreshableview_default_header, refreshableView, false);
        imageView = (ImageView) headerView.findViewById(R.id.coolrefresh_defaultheader_imageView);
        textView = (TextView) headerView.findViewById(R.id.coolrefresh_defaultheader_textView);
        progressImageView = (ImageView) headerView.findViewById(R.id.coolrefresh_defaultheader_progress_imageView);

        mFlipAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(mRotateAniTime);
        mFlipAnimation.setFillAfter(true);

        mReverseFlipAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        mReverseFlipAnimation.setDuration(mRotateAniTime);
        mReverseFlipAnimation.setFillAfter(true);

        imageView.setAnimation(mFlipAnimation);
        materialProgressDrawable = new MaterialProgressDrawable(context, progressImageView);
        materialProgressDrawable.setColorSchemeColors(Color.WHITE);
        materialProgressDrawable.setAlpha(255);
        progressImageView.setImageDrawable(materialProgressDrawable);
        headerView.setBackgroundColor(backgroundColor);
        return headerView;
    }

    @Override
    public void onPullBegin(RefreshableView refreshableView) {
        progressImageView.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        textView.setText(getResources().getString(R.string.pull_down_to_refresh));
        isDownArrow = true;
    }

    private boolean isDownArrow = true;

    @Override
    public void onPositionChange(RefreshableView refreshableView, int status, int dy, int currentDistance) {
        int offsetToRefresh = getConfig().offsetToRefresh(refreshableView, headerView);
        if (status == RefreshableView.PULL_STATUS_TOUCH_MOVE) {
            if (currentDistance < offsetToRefresh) {
                if (!isDownArrow) {
                    textView.setText(getResources().getString(R.string.pull_down_to_refresh));
                    imageView.clearAnimation();
                    imageView.startAnimation(mReverseFlipAnimation);
                    isDownArrow = true;
                }
            } else {
                if (isDownArrow) {
                    textView.setText(getResources().getString(R.string.release_to_refresh));
                    imageView.clearAnimation();
                    imageView.startAnimation(mFlipAnimation);
                    isDownArrow = false;
                }
            }
        }
    }

    @Override
    public void onRefreshing(RefreshableView refreshableView) {
        textView.setText(getResources().getString(R.string.refreshing));
        imageView.clearAnimation();
        materialProgressDrawable.start();
        imageView.setVisibility(View.GONE);
        progressImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onReset(RefreshableView refreshableView, boolean pullRelease) {
        textView.setText(getResources().getString(R.string.pull_down_to_refresh));
        materialProgressDrawable.stop();
        imageView.setVisibility(View.GONE);
        progressImageView.setVisibility(View.GONE);
        imageView.clearAnimation();
    }

    @Override
    public void onPullRefreshComplete(RefreshableView refreshableView) {
        textView.setText(getResources().getString(R.string.refresh_complete));
        materialProgressDrawable.stop();
        imageView.setVisibility(View.GONE);
        progressImageView.setVisibility(View.GONE);
        imageView.clearAnimation();
    }

    private Resources getResources() {
        return headerView.getResources();
    }


    @Override
    public Config getConfig() {
        return config;
    }

    private DefaultConfig config = new DefaultConfig() {
        @Override
        public int offsetToRefresh(RefreshableView refreshableView, View headerView) {
            return (int) (headerView.getMeasuredHeight() / 3 * 1.2f);
        }

        @Override
        public int offsetToKeepHeaderWhileLoading(RefreshableView refreshableView, View headerView) {
            return headerView.getMeasuredHeight() / 3;
        }

        @Override
        public int totalDistance(RefreshableView refreshableView, View headerView) {
            return headerView.getMeasuredHeight();
        }
    };
}
