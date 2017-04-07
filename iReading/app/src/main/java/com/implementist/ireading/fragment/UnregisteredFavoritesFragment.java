package com.implementist.ireading.fragment;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.implementist.ireading.R;
import com.implementist.ireading.activity.LoginActivity;
import com.implementist.ireading.activity.MainActivity;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */
public class UnregisteredFavoritesFragment extends BaseFragment {

    Button btnLogin;

    @Override
    public int bindLayout() {
        return R.layout.fragment_favorites_unregistered;
    }

    @Override
    public void initView(View view) {
        btnLogin = $(R.id.btnLogin);
    }

    @Override
    public void setListener() {
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetOnClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                ((MainActivity) getActivity()).startActivity(LoginActivity.class);
                break;
        }
    }
}
