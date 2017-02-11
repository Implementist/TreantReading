package com.implementist.treantreading;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andview.refreshview.callback.IHeaderCallBack;
import com.andview.refreshview.utils.Utils;

import java.util.Calendar;

public class RefreshViewHeader extends LinearLayout implements IHeaderCallBack {
    private ViewGroup mContent;
    private ProgressBar mProgressBar;
    private TextView tvRefreshState;
    private TextView tvLastRefreshTime;
    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;
    private final int ROTATE_ANIM_DURATION = 180;

    public RefreshViewHeader(Context context) {
        super(context);
        initView(context);
    }

    public RefreshViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContent = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.rfv_header, this);
        tvRefreshState = (TextView) findViewById(R.id.tvRefreshState);
        tvLastRefreshTime = (TextView) findViewById(R.id.tvLastRefreshTime);
        mProgressBar = (ProgressBar) findViewById(R.id.rfv_header_progressbar);

        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateDownAnim.setDuration(0);
        mRotateDownAnim.setFillAfter(true);
    }

    public void setRefreshTime(long lastRefreshTime) {
        // get current time
        Calendar mCalendar = Calendar.getInstance();
        long refreshTime = mCalendar.getTimeInMillis();
        long interval = refreshTime - lastRefreshTime;
        int minutes = (int) (interval / 1000 / 60);
        String refreshTimeText;
        Resources resources = getContext().getResources();
        if (minutes < 1) {
            refreshTimeText = resources.getString(R.string.just_refresh);
        } else if (minutes < 60) {
            refreshTimeText = resources.getString(R.string.refreshed_minutes_ago);
            refreshTimeText = Utils.format(refreshTimeText, minutes);
        } else if (minutes < 60 * 24) {
            refreshTimeText = resources.getString(R.string.refreshed_hours_before);
            refreshTimeText = Utils.format(refreshTimeText, minutes / 60);
        } else {
            refreshTimeText = resources
                    .getString(R.string.refreshed_days_before);
            refreshTimeText = Utils.format(refreshTimeText, minutes / 60 / 24);
        }
        tvLastRefreshTime.setText(refreshTimeText);
    }

    /**
     * hide footer when disable pull refresh
     */
    public void hide() {
        setVisibility(View.GONE);
    }

    public void show() {
        setVisibility(View.VISIBLE);
    }

    @Override
    public void onStateNormal() {
        mProgressBar.setVisibility(View.GONE);
        tvRefreshState.setText(R.string.pull_down_to_refresh);
    }

    @Override
    public void onStateReady() {
        mProgressBar.setVisibility(View.GONE);
        tvRefreshState.setText(R.string.release_to_refresh);
        tvLastRefreshTime.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStateRefreshing() {
        mProgressBar.setVisibility(View.VISIBLE);
        tvRefreshState.setText(R.string.loading);
    }

    @Override
    public void onStateFinish(boolean success) {
        mProgressBar.setVisibility(View.GONE);
        tvRefreshState.setText(success ? R.string.loading_success : R.string.loading_failed);
        tvLastRefreshTime.setVisibility(View.GONE);
    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY, int deltaY) {

    }

    @Override
    public int getHeaderHeight() {
        return getMeasuredHeight();
    }
}
