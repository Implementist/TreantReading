package com.implementist.ireading.fragment;


import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dx.dxloadingbutton.lib.LoadingButton;
import com.implementist.ireading.MyApplication;
import com.implementist.ireading.R;
import com.implementist.ireading.activity.MainActivity;
import com.implementist.ireading.activity.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */
public class RegisterStep3Fragment extends BaseFragment implements TextWatcher,
        LoadingButton.AnimationEndListener {

    private EditText editRegisterPassword, editRegisterRepeat;
    private ImageView imgRegisterClearPassword, imgRegisterClearRepeat;
    private LoadingButton btnRegister;
    private String strPhoneNumber;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param phoneNumber Parameter 1.
     * @return A new instance of fragment RegisterStep3Fragment.
     */

    public static RegisterStep3Fragment newInstance(String phoneNumber) {
        RegisterStep3Fragment fragment = new RegisterStep3Fragment();
        Bundle args = new Bundle();
        args.putString("phoneNumber", phoneNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_register_step3;
    }

    @Override
    public void initView(View view) {

        if (getArguments() != null) {
            strPhoneNumber = getArguments().getString("phoneNumber");
        }

        editRegisterPassword = (EditText) view.findViewById(R.id.editRegisterPassword);
        editRegisterRepeat = (EditText) view.findViewById(R.id.editRegisterRepeat);
        imgRegisterClearPassword = (ImageView) view.findViewById(R.id.imgRegisterClearPassword);
        imgRegisterClearRepeat = (ImageView) view.findViewById(R.id.imgRegisterClearRepeat);
        btnRegister = (LoadingButton) view.findViewById(R.id.btnRegister);
    }

    @Override
    public void setListener() {
        editRegisterPassword.addTextChangedListener(this);
        editRegisterRepeat.addTextChangedListener(this);
        imgRegisterClearPassword.setOnClickListener(this);
        imgRegisterClearRepeat.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnRegister.setAnimationEndListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetOnClick(View v) {
        switch (v.getId()) {
            case R.id.imgRegisterClearPassword:
                editRegisterPassword.setText("");
                break;

            case R.id.imgRegisterClearRepeat:
                editRegisterRepeat.setText("");
                break;

            case R.id.btnRegister:
                if (editRegisterRepeat.getText().length() > 0 && editRegisterPassword.getText()
                        .toString().equals(editRegisterRepeat.getText().toString())) {
                    btnRegister.startLoading();
                    VolleyPost();
                } else if (editRegisterPassword.getText().length() == 0)
                    ((RegisterActivity) getActivity()).showToast("请输入密码");
                else if (editRegisterRepeat.getText().length() == 0)
                    ((RegisterActivity) getActivity()).showToast("请再次输入密码以确认");
                else
                    ((RegisterActivity) getActivity()).showToast("两次输入不匹配，请检查");
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

        if (!editRegisterPassword.getText().toString().equals(""))
            imgRegisterClearPassword.setVisibility(View.VISIBLE);
        else
            imgRegisterClearPassword.setVisibility(View.INVISIBLE);


        if (!editRegisterRepeat.getText().toString().equals(""))
            imgRegisterClearRepeat.setVisibility(View.VISIBLE);
        else
            imgRegisterClearRepeat.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAnimationEnd(LoadingButton.AnimationType animationType) {
        if (animationType == LoadingButton.AnimationType.SUCCESSFUL) {
            ((RegisterActivity) getActivity()).startActivity(MainActivity.class);
        }
    }

    // 定义POST请求的方法
    private void VolleyPost() {
        //请求地址
        String url = "http://ireading.imwork.net/iReading/*";
        String tag = "RegisterByPhoneNumberStep3";

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
                            String result = jsonObject.getString("RegisterResult");
                            if (result.equals("true")) {
                                btnRegister.loadingSuccessful();
                            } else {
                                btnRegister.loadingFailed();
                                ((RegisterActivity) getActivity()).showToast("服务器繁忙，请稍后重试");
                            }
                        } catch (JSONException e) {
                            ((RegisterActivity) getActivity()).showToast("网络请求发生错误，稍后请重试");
                            btnRegister.loadingFailed();
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((RegisterActivity) getActivity()).showToast("网络请求发生错误，稍后请重试");
                btnRegister.loadingFailed();
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("RequestType", "Register");
                params.put("RegisterType", "RegisterByPhoneNumber");
                params.put("RegisterStep", "3");
                params.put("PhoneNumber", strPhoneNumber);
                params.put("Password", editRegisterPassword.getText().toString());
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }
}
