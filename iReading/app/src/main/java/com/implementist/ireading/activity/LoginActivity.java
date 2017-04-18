package com.implementist.ireading.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.implementist.ireading.R;
import com.implementist.ireading.fragment.LoginByPasswordFragment;
import com.implementist.ireading.fragment.LoginByPhoneFragment;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */

public class LoginActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private LinearLayout lytBackFromLogin;
    private TextView txtLoginUserAgreement, txtRegister;
    private LoginByPhoneFragment frgLoginByPhone;
    private LoginByPasswordFragment frgLoginByPassword;
    private RadioGroup rdgLoginTab;

    @Override
    public void initParams(Bundle params) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(View view) {
        lytBackFromLogin = $(R.id.lytBackFromLogin);
        txtLoginUserAgreement = $(R.id.txtLoginUserAgreement);
        txtRegister = $(R.id.txtRegister);
        rdgLoginTab = $(R.id.rdgLoginTab);

        frgLoginByPassword = new LoginByPasswordFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.login_content, frgLoginByPassword).commit();
    }

    @Override
    public void setListener() {
        lytBackFromLogin.setOnClickListener(this);
        txtLoginUserAgreement.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
        rdgLoginTab.setOnCheckedChangeListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetOnClick(View v) {
        //TODO:Wait to be written.
        switch (v.getId()) {
            case R.id.lytBackFromLogin:
                finish();
                break;
            case R.id.txtLoginUserAgreement:
                break;
            case R.id.txtRegister:
                startActivity(RegisterActivity.class);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rbLoginByPassword:
                if (null == frgLoginByPassword)
                    frgLoginByPassword = new LoginByPasswordFragment();

                //带动画替换Fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.login_content, frgLoginByPassword)
                        .commit();
                break;

            case R.id.rbLoginByPhone:
                if (null == frgLoginByPhone)
                    frgLoginByPhone = new LoginByPhoneFragment();

                //带动画替换Fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.login_content, frgLoginByPhone)
                        .commit();
                break;
        }
    }
}
