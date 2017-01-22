package com.implementist.treantreading;

import android.view.View;

import java.util.HashSet;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */

class ProxyPullHeader implements PullHeader {

    private HashSet<OnPullListener> listeners = new HashSet<>(3);

    private PullHeader reaPullHeader;

    public ProxyPullHeader(PullHeader reaPullHeader) {
        this.reaPullHeader = reaPullHeader;
    }

    public void setPullHandler(PullHeader pullHeader) {
        reaPullHeader = pullHeader;
    }

    @Override
    public View createHeaderView(RefreshableView refreshableView) {
        return reaPullHeader.createHeaderView(refreshableView);
    }

    @Override
    public Config getConfig() {
        return reaPullHeader.getConfig();
    }

    @Override
    public void onPullBegin(RefreshableView refreshableView) {
        reaPullHeader.onPullBegin(refreshableView);
        for (OnPullListener listener : listeners) {
            listener.onPullBegin(refreshableView);
        }
    }

    @Override
    public void onPositionChange(RefreshableView refreshableView, int status, int dy, int currentDistance) {
        reaPullHeader.onPositionChange(refreshableView, status, dy, currentDistance);
        for (OnPullListener listener : listeners) {
            listener.onPositionChange(refreshableView, status, dy, currentDistance);
        }
    }

    @Override
    public void onRefreshing(RefreshableView refreshableView) {
        reaPullHeader.onRefreshing(refreshableView);
        for (OnPullListener listener : listeners) {
            listener.onRefreshing(refreshableView);
        }
    }

    @Override
    public void onReset(RefreshableView refreshableView, boolean pullRelease) {
        reaPullHeader.onReset(refreshableView, pullRelease);
        for (OnPullListener listener : listeners) {
            listener.onReset(refreshableView, pullRelease);
        }
    }

    @Override
    public void onPullRefreshComplete(RefreshableView refreshableView) {
        reaPullHeader.onPullRefreshComplete(refreshableView);
        for (OnPullListener listener : listeners) {
            listener.onPullRefreshComplete(refreshableView);
        }
    }

    public void addListener(OnPullListener onPullListener) {
        listeners.add(onPullListener);
    }

    public void removeListener(OnPullListener onPullListener) {
        listeners.remove(onPullListener);
    }
}
