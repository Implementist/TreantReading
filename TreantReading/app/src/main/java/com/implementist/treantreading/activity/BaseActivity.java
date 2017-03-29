package com.implementist.treantreading.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */

public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {
    /**
     * 是否沉浸状态栏
     **/
    protected boolean isSetStatusBar = true;
    /**
     * 是否允许全屏
     **/
    private boolean mAllowFullScreen = true;
    /**
     * 是否禁止旋转屏幕
     **/
    private boolean isAllowScreenRotate = false;
    /**
     * 当前Activity渲染的视图View
     **/
    private View mContextView = null;
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    /**
     * View点击
     **/
    public abstract void widgetOnClick(View v);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "BaseActivity-->onCreate()");
        Bundle bundle = getIntent().getExtras();
        initParams(bundle);
        View mView = bindView();

        if (null == mView) {
            mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        } else
            mContextView = mView;

        if (mAllowFullScreen)
            requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (isSetStatusBar) {
            steepStatusBar();
        }
        setContentView(mContextView);

        if (!isAllowScreenRotate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        initView(mContextView);
        setListener();
        doBusiness(this);
    }

    /**
     * [沉浸状态栏]
     */
    private void steepStatusBar() {
        // 透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

    }

    /**
     * [初始化参数]
     *
     * @param params 参数
     */
    public abstract void initParams(Bundle params);

    /**
     * [绑定视图]
     */
    public abstract View bindView();

    /**
     * [绑定布局]
     */
    public abstract int bindLayout();

    /**
     * [初始化控件]
     *
     * @param view 控件
     */
    public abstract void initView(final View view);

    /**
     * [绑定控件]
     *
     * @param resId 资源Id
     */
    protected <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    /**
     * [设置监听]
     */
    public abstract void setListener();

    @Override
    public void onClick(View v) {
        widgetOnClick(v);
    }

    /**
     * [业务操作]
     *
     * @param mContext 上下文
     */
    public abstract void doBusiness(Context mContext);

    /**
     * [页面跳转]
     *
     * @param cls 类名
     */
    public void startActivity(Class<?> cls) {
        startActivity(new Intent(BaseActivity.this, cls));
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param cls    类名
     * @param bundle bundle
     */
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls         类名
     * @param bundle      bundle
     * @param requestCode 请求码
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    /**
     * [简化Toast]
     *
     * @param msg 信息
     */
    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * [是否允许全屏]
     *
     * @param isAllowFullScreen 是否允许全屏
     */
    public void setAllowFullScreen(boolean isAllowFullScreen) {
        this.mAllowFullScreen = isAllowFullScreen;
    }

    /**
     * [是否设置沉浸状态栏]
     *
     * @param isSetStatusBar 是否设置状态栏
     */
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }

    /**
     * [是否允许屏幕旋转]
     *
     * @param isAllowScreenRotate 是否允许屏幕旋转
     */
    public void setScreenRotate(boolean isAllowScreenRotate) {
        this.isAllowScreenRotate = isAllowScreenRotate;
    }
}
