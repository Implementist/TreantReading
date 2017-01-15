package com.implementist.treantreading;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    private Button button;
    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.button:
                //用简化了的toast和startActivity
                showToast("toast");
                startActivity(MainActivity.class);
                break;
        }
    }

    @Override
    public void initParams(Bundle params) {
        //解析bundle内容或者设置是否旋转，沉浸，全屏
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
        button=$(R.id.button);
    }

    @Override
    public void setListener() {
        button.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
