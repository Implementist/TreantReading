package com.implementist.ireading.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.implementist.ireading.R;
import com.implementist.ireading.activity.RegisterActivity;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */

public class RegisterStep2Fragment extends BaseFragment implements TextWatcher {

    private EditText editRegisterSecurityCode;
    private ImageView imgRegisterClearSecurityCode;
    private Button btnRegisterSubmitSecurityCode;
    private String strPhoneNumber;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param phoneNumber Parameter 1.
     * @return A new instance of fragment RegisterStep2Fragment.
     */
    public static RegisterStep2Fragment newInstance(String phoneNumber) {
        RegisterStep2Fragment fragment = new RegisterStep2Fragment();
        Bundle args = new Bundle();
        args.putString("phoneNumber", phoneNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_register_step2;
    }

    @Override
    public void initView(View view) {

        if (getArguments() != null) {
            strPhoneNumber = getArguments().getString("phoneNumber");
        }

        editRegisterSecurityCode = (EditText) view.findViewById(R.id.editRegisterSecurityCode);
        imgRegisterClearSecurityCode = (ImageView) view.findViewById(R.id.imgRegisterClearSecurityCode);
        btnRegisterSubmitSecurityCode = (Button) view.findViewById(R.id.btnRegisterSubmitSecurityCode);
    }

    @Override
    public void setListener() {
        editRegisterSecurityCode.addTextChangedListener(this);
        imgRegisterClearSecurityCode.setOnClickListener(this);
        btnRegisterSubmitSecurityCode.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetOnClick(View v) {
        switch (v.getId()) {
            case R.id.imgRegisterClearSecurityCode:
                editRegisterSecurityCode.setText("");
                break;

            case R.id.btnRegisterSubmitSecurityCode:
                //TODO: 对照已经发送到客户端的验证码和信息中的验证码是否一致，对就跳转，不对就Toast
                //TODO:以下两行为测试代码，测试后请删除
                ((RegisterActivity) getActivity()).frgRegisterStep3 = RegisterStep3Fragment.newInstance(strPhoneNumber);
                //改变选中的radioButton，跳转页面
                ((RadioButton) getActivity().findViewById(R.id.rbStep3)).setChecked(true);
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

        if (!editRegisterSecurityCode.getText().toString().equals(""))
            imgRegisterClearSecurityCode.setVisibility(View.VISIBLE);
        else
            imgRegisterClearSecurityCode.setVisibility(View.INVISIBLE);

        if (editRegisterSecurityCode.getText().length() == 6)
            btnRegisterSubmitSecurityCode.setEnabled(true);
        else
            btnRegisterSubmitSecurityCode.setEnabled(false);
    }
}
