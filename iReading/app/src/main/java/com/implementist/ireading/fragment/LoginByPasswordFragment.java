package com.implementist.ireading.fragment;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dx.dxloadingbutton.lib.LoadingButton;
import com.implementist.ireading.HttpRequestUtils;
import com.implementist.ireading.R;
import com.implementist.ireading.Utils;
import com.implementist.ireading.activity.LoginActivity;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */
public class LoginByPasswordFragment extends BaseFragment implements TextWatcher,
        LoadingButton.AnimationEndListener, CompoundButton.OnCheckedChangeListener {

    private AutoCompleteTextView atxtAccountNumber;
    private EditText editPassword;
    private LoadingButton btnLoginByPassword;
    private TextView txtRetrievePassword;
    private ImageView imgClearAccountNumber, imgClearPassword;
    private CheckBox cbxPasswordVisibility;

    @Override
    public int bindLayout() {
        return R.layout.fragment_login_by_password;
    }

    @Override
    public void initView(View view) {
        atxtAccountNumber = (AutoCompleteTextView) view.findViewById(R.id.atxtAccountNumber);
        editPassword = (EditText) view.findViewById(R.id.editPassword);
        btnLoginByPassword = (LoadingButton) view.findViewById(R.id.btnLoginByPassword);
        txtRetrievePassword = (TextView) view.findViewById(R.id.txtRetrievePassword);
        imgClearAccountNumber = (ImageView) view.findViewById(R.id.imgClearAccountNumber);
        imgClearPassword = (ImageView) view.findViewById(R.id.imgClearPassword);
        cbxPasswordVisibility = (CheckBox) view.findViewById(R.id.cbxPasswordVisibility);
    }

    @Override
    public void setListener() {
        btnLoginByPassword.setOnClickListener(this);
        btnLoginByPassword.setAnimationEndListener(this);
        txtRetrievePassword.setOnClickListener(this);
        atxtAccountNumber.addTextChangedListener(this);
        imgClearAccountNumber.setOnClickListener(this);
        editPassword.addTextChangedListener(this);
        imgClearPassword.setOnClickListener(this);
        cbxPasswordVisibility.setOnCheckedChangeListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetOnClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoginByPassword:
                if (atxtAccountNumber.getText().length() == 0)
                    ((LoginActivity) getActivity()).showToast("请输入用户名、手机或邮箱");
                else if (editPassword.getText().length() == 0)
                    ((LoginActivity) getActivity()).showToast("请输入密码");
                else {
                    int loginType = Utils.judgeLoginType(atxtAccountNumber.getText().toString());
                    btnLoginByPassword.startLoading();
                    HttpRequestUtils.LoginByPasswordRequest(loginType, btnLoginByPassword,
                            (LoginActivity) getActivity(), atxtAccountNumber.getText().toString(),
                            editPassword.getText().toString());
                }
                break;

            case R.id.txtRetrievePassword:
                //TODO: 跳转到找回密码页面，并把atxtAccountNumber传过去放在账号栏里
                break;

            case R.id.imgClearAccountNumber:
                atxtAccountNumber.setText("");
                break;

            case R.id.imgClearPassword:
                editPassword.setText("");
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
        if (!atxtAccountNumber.getText().toString().equals(""))
            imgClearAccountNumber.setVisibility(View.VISIBLE);
        else
            imgClearAccountNumber.setVisibility(View.INVISIBLE);

        if (!editPassword.getText().toString().equals(""))
            imgClearPassword.setVisibility(View.VISIBLE);
        else
            imgClearPassword.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAnimationEnd(LoadingButton.AnimationType animationType) {
        if (animationType == LoadingButton.AnimationType.SUCCESSFUL)
            getActivity().finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked) {
            editPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            cbxPasswordVisibility.setBackgroundResource(R.drawable.ic_visible);
        } else {
            editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            cbxPasswordVisibility.setBackgroundResource(R.drawable.ic_invisible);
        }
    }
}
