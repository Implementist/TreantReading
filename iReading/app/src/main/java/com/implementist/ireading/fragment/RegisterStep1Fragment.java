package com.implementist.ireading.fragment;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dx.dxloadingbutton.lib.LoadingButton;
import com.implementist.ireading.MyApplication;
import com.implementist.ireading.R;
import com.implementist.ireading.Utils;
import com.implementist.ireading.activity.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
                if (Utils.isPhoneNumber(editRegisterPhoneNumber.getText().toString()) && chbUserAgreement.isChecked()) {
                    btnRegisterGetSecurityCode.startLoading();
                    VolleyPost();
                } else if (!Utils.isPhoneNumber(editRegisterPhoneNumber.getText().toString()))
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
        } else if (animationType == LoadingButton.AnimationType.FAILED) {
            //TODO: It does not work. I think this is a bug waiting to be fixed.
            ((RegisterActivity) getActivity()).showToast("该手机号已经被注册，验证手机号后可直接登录");
        }
    }

    // 定义POST请求的方法
    private void VolleyPost() {
        //请求地址
        String url = "http://ireading.imwork.net/iReading/*";
        String tag = "RegisterByPhoneNumberStep1";

        //取得请求队列
        RequestQueue requestQueue = MyApplication.getRequestQueue();

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
                            String result = jsonObject.getString("IsNew");
                            if (result.equals("true")) {
                                btnRegisterGetSecurityCode.loadingSuccessful();
                            } else {
                                btnRegisterGetSecurityCode.loadingFailed();
                                ((RegisterActivity) getActivity()).showToast("该手机号已经被注册，验证手机号后可直接登录");
                            }
                        } catch (JSONException e) {
                            ((RegisterActivity) getActivity()).showToast("网络请求发生错误，稍后请重试");
                            btnRegisterGetSecurityCode.loadingFailed();
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((RegisterActivity) getActivity()).showToast("网络请求发生错误，稍后请重试");
                btnRegisterGetSecurityCode.loadingFailed();
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("RequestType", "Register");
                params.put("RegisterType", "RegisterByPhoneNumber");
                params.put("RegisterStep", "1");
                params.put("PhoneNumber", editRegisterPhoneNumber.getText().toString());
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }
}
