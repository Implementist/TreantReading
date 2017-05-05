package com.implementist.ireading.fragment;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dx.dxloadingbutton.lib.LoadingButton;
import com.implementist.ireading.utils.HttpRequestUtils;
import com.implementist.ireading.R;
import com.implementist.ireading.utils.CommonUtils;
import com.implementist.ireading.activity.RegisterActivity;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */
public class RegisterStep1Fragment extends BaseFragment implements TextWatcher,
        LoadingButton.AnimationEndListener {

    private ImageView imgRegisterClearPhoneNumber;
    private EditText editRegisterPhoneNumber;
    private LoadingButton btnRegisterGetSecurityCode;
    private CheckBox chbUserAgreement;
    private TextView txtRegisterUserAgreement;

    @Override
    public int bindLayout() {
        return R.layout.fragment_register_step1;
    }

    @Override
    public void initView(View view) {
        imgRegisterClearPhoneNumber = (ImageView) view.findViewById(R.id.imgRegisterClearPhoneNumber);
        editRegisterPhoneNumber = (EditText) view.findViewById(R.id.editRegisterPhoneNumber);
        btnRegisterGetSecurityCode = (LoadingButton) view.findViewById(R.id.btnRegisterGetSecurityCode);
        chbUserAgreement = (CheckBox) view.findViewById(R.id.chbUserAgreement);
        txtRegisterUserAgreement = (TextView) view.findViewById(R.id.txtRegisterUserAgreement);
    }

    @Override
    public void setListener() {
        editRegisterPhoneNumber.addTextChangedListener(this);
        imgRegisterClearPhoneNumber.setOnClickListener(this);
        btnRegisterGetSecurityCode.setOnClickListener(this);
        btnRegisterGetSecurityCode.setAnimationEndListener(this);
        txtRegisterUserAgreement.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetOnClick(View v) {
        switch (v.getId()) {
            case R.id.imgRegisterClearPhoneNumber:
                editRegisterPhoneNumber.setText("");
                break;

            case R.id.btnRegisterGetSecurityCode:
                if (CommonUtils.isPhoneNumber(editRegisterPhoneNumber.getText().toString()) && chbUserAgreement.isChecked()) {
                    btnRegisterGetSecurityCode.startLoading();
                    HttpRequestUtils.RegisterStep1Request(btnRegisterGetSecurityCode,
                            (RegisterActivity) getActivity(), editRegisterPhoneNumber.getText().toString());
                } else if (!CommonUtils.isPhoneNumber(editRegisterPhoneNumber.getText().toString()))
                    ((RegisterActivity) getActivity()).showToast("请输入正确的11位手机号码");
                else if (!chbUserAgreement.isChecked())
                    ((RegisterActivity) getActivity()).showToast("您还没有接受iReading用户协议");
                break;

            case R.id.txtRegisterUserAgreement:
                //TODO: Jump to User Agreement Page.
                break;
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
        if (!editRegisterPhoneNumber.getText().toString().equals(""))
            imgRegisterClearPhoneNumber.setVisibility(View.VISIBLE);
        else
            imgRegisterClearPhoneNumber.setVisibility(View.INVISIBLE);

        if (editRegisterPhoneNumber.getText().length() == 11)
            btnRegisterGetSecurityCode.setEnabled(true);
        else
            btnRegisterGetSecurityCode.setEnabled(false);
    }

    @Override
    public void onAnimationEnd(LoadingButton.AnimationType animationType) {
        if (animationType == LoadingButton.AnimationType.SUCCESSFUL) {

            ((RegisterActivity) getActivity()).frgRegisterStep2 = RegisterStep2Fragment
                    .newInstance(editRegisterPhoneNumber.getText().toString());

            //改变选中的radioButton，跳转页面
            ((RadioButton) getActivity().findViewById(R.id.rbStep2)).setChecked(true);
        }
    }
}
