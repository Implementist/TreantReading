package com.implementist.ireading.fragment;


import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.implementist.ireading.R;
import com.implementist.ireading.Utils;
import com.implementist.ireading.activity.LoginActivity;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */

public class LoginByPhoneFragment extends BaseFragment implements TextWatcher {

    private AutoCompleteTextView atxtPhoneNumber;
    private EditText editSecurityCode;
    private Button btnSecurityCode, btnLoginByPhone;
    private ImageView imgClearPhoneNumber, imgClearSecurityCode;

    private Handler handler = new Handler();
    private Runnable runnable;

    private int timeCount = 60;

    @Override
    public int bindLayout() {
        return R.layout.fragment_login_by_phone;
    }

    @Override
    public void initView(View view) {
        atxtPhoneNumber = (AutoCompleteTextView) view.findViewById(R.id.atxtPhoneNumber);
        btnSecurityCode = (Button) view.findViewById(R.id.btnSecurityCode);
        btnLoginByPhone = (Button) view.findViewById(R.id.btnLoginByPhone);
        editSecurityCode = (EditText) view.findViewById(R.id.editSecurityCode);
        imgClearPhoneNumber = (ImageView) view.findViewById(R.id.imgClearPhoneNumber);
        imgClearSecurityCode = (ImageView) view.findViewById(R.id.imgClearSecurityCode);

        //设定计时器
        runnable = new Runnable() {
            @Override
            public void run() {
                if (timeCount > 0) {
                    btnSecurityCode.setText("  " + timeCount-- + "s重新获取  ");
                    handler.postDelayed(this, 1000);
                } else {
                    btnSecurityCode.setText("  重新获取  ");
                    btnSecurityCode.setEnabled(true);
                    timeCount = 60;
                    //停止计时器
                    handler.removeCallbacks(runnable);
                }
            }
        };
    }

    @Override
    public void setListener() {
        btnSecurityCode.setOnClickListener(this);
        btnLoginByPhone.setOnClickListener(this);
        atxtPhoneNumber.addTextChangedListener(this);
        imgClearPhoneNumber.setOnClickListener(this);
        editSecurityCode.addTextChangedListener(this);
        imgClearSecurityCode.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetOnClick(View v) {
        switch (v.getId()) {
            case R.id.btnSecurityCode:
                if (Utils.isPhoneNumber(atxtPhoneNumber.getText().toString())) {
                    //TODO: 待实现发送信息验证码的代码模块，获取验证码（信息和发回客户端的验证码两种）;

                    btnSecurityCode.setEnabled(false);
                    handler.postDelayed(runnable, 0);//开始计时器
                } else {
                    ((LoginActivity) getActivity()).showToast("请输入正确的手机号");
                    atxtPhoneNumber.setText("");
                }
                break;

            case R.id.btnLoginByPhone:
                String strPhoneNumber = atxtPhoneNumber.getText().toString(),
                        strSecurityNumber = editSecurityCode.getText().toString();
                if (Utils.isPhoneNumber(strPhoneNumber) && (!strSecurityNumber.equals(""))) {
                    btnLoginByPhone.setEnabled(false);
                    //TODO: 用已经获取的验证码和用户输入的验证码作比较,两次获取的手机号码必须验证是否一致！
                    //正确就跳转，错误就报错（提示框）,验证错误或跳转前记得改变btnLoginByPhone的enable为true
                    //验证错误就enable登陆键，同时错误计数，超过三次锁号24小时
                } else if (!Utils.isPhoneNumber(strPhoneNumber))
                    ((LoginActivity) getActivity()).showToast("请输入正确的手机号");
                else
                    ((LoginActivity) getActivity()).showToast("请输入收到的验证码");
                break;

            case R.id.imgClearPhoneNumber:
                atxtPhoneNumber.setText("");
                break;

            case R.id.imgClearSecurityCode:
                editSecurityCode.setText("");
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!atxtPhoneNumber.getText().toString().equals(""))
            imgClearPhoneNumber.setVisibility(View.VISIBLE);
        else
            imgClearPhoneNumber.setVisibility(View.INVISIBLE);

        if (!editSecurityCode.getText().toString().equals(""))
            imgClearSecurityCode.setVisibility(View.VISIBLE);
        else
            imgClearSecurityCode.setVisibility(View.INVISIBLE);
    }
}
