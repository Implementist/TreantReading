package com.implementist.ireading.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    private View mContextView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //这一组嵌套判断是为了避免Fragment在replace时重复加载onCreateView方法
        if (null == mContextView) {
            mContextView = inflater.inflate(bindLayout(), container, false);
            initView(mContextView);
            setListener();
            doBusiness(getActivity());
        } else {
            //缓存的mContextView需要判断是否已经被加过parent，如果有parent需要从parent删除，
            //否则会发生这个mContextView已经有parent的错误。
            ViewGroup parent = (ViewGroup) mContextView.getParent();
            if (null != parent) {
                parent.removeView(mContextView);
            }
        }
        return mContextView;
    }

    /**
     * [绑定布局]
     *
     * @return 布局文件编号
     */
    public abstract int bindLayout();

    /**
     * [初始化控件]
     *
     * @param view 此Fragment的实例
     */
    public abstract void initView(final View view);

    /**
     * [绑定控件]
     *
     * @param resId 资源Id
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T $(int resId) {
        return (T) mContextView.findViewById(resId);
    }

    /**
     * [设置监听]
     */
    public abstract void setListener();

    /**
     * [业务操作]
     *
     * @param mContext 容器Activity
     */
    public abstract void doBusiness(Context mContext);

    /**
     * View点击
     **/
    public abstract void widgetOnClick(View v);

    @Override
    public void onClick(View v) {
        if (fastClick())
            widgetOnClick(v);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T $(View view, int resId) {
        return (T) view.findViewById(resId);
    }

    /**
     * [防止快速点击]
     *
     * @return 是否允许连续点击
     */
    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }
}
