package com.implementist.ireading.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.implementist.ireading.R;
import com.implementist.ireading.fragment.RegisterStep1Fragment;
import com.implementist.ireading.fragment.RegisterStep2Fragment;
import com.implementist.ireading.fragment.RegisterStep3Fragment;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */
public class RegisterActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private LinearLayout lytBackFromRegister;
    private RadioGroup rdgRegisterSteps;
    private RegisterStep1Fragment frgRegisterStep1;
    public RegisterStep2Fragment frgRegisterStep2;
    public RegisterStep3Fragment frgRegisterStep3;

    @Override
    public void initParams(Bundle params) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView(View view) {
        lytBackFromRegister = $(R.id.lytBackFromRegister);

        frgRegisterStep1 = new RegisterStep1Fragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.register_content, frgRegisterStep1)
                .commit();

        rdgRegisterSteps = $(R.id.rdgRegisterSteps);
    }

    @Override
    public void setListener() {
        lytBackFromRegister.setOnClickListener(this);
        rdgRegisterSteps.setOnCheckedChangeListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetOnClick(View v) {
        switch (v.getId()) {
            case R.id.lytBackFromRegister:
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rbStep2:
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.register_content, frgRegisterStep2)
                        .commit();
                break;

            case R.id.rbStep3:
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.register_content, frgRegisterStep3)
                        .commit();
                break;
        }
    }
}
