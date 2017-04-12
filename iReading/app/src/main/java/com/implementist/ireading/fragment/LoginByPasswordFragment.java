package com.implementist.ireading.fragment;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.implementist.ireading.activity.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */
public class LoginByPasswordFragment extends BaseFragment implements TextWatcher,
        LoadingButton.AnimationEndListener {

    private final static String[] LOGIN_TYPE = {
            "LoginByPhoneNumber", "LoginByEmailAddress", "LoginByUserName"};

    private AutoCompleteTextView atxtAccountNumber;
    private EditText editPassword;
    private LoadingButton btnLoginByPassword;
    private TextView txtRetrievePassword;
    private ImageView imgClearAccountNumber, imgClearPassword;

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
                    VolleyPost(loginType);
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

    // 定义POST请求的方法
    private void VolleyPost(final int typeID) {
        //请求地址
        String url = "http://ireading.imwork.net/iReading/*";
        String tag = LOGIN_TYPE[typeID];

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
                            String result = jsonObject.getString("Result");
                            if (result.equals("success")) {
                                btnLoginByPassword.loadingSuccessful();
                            } else {
                                ((LoginActivity) getActivity()).showToast("账号或密码错误，请重新输入");
                                btnLoginByPassword.loadingFailed();
                            }
                        } catch (JSONException e) {
                            ((LoginActivity) getActivity()).showToast("网络请求发生错误，稍后请重试");
                            btnLoginByPassword.loadingFailed();
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((LoginActivity) getActivity()).showToast("网络请求发生错误，稍后请重试");
                btnLoginByPassword.loadingFailed();
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("RequestType", "Login");
                params.put("LoginType", LOGIN_TYPE[typeID]);
                params.put("AccountNumber", atxtAccountNumber.getText().toString());
                params.put("Password", editPassword.getText().toString());
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }

    @Override
    public void onAnimationEnd(LoadingButton.AnimationType animationType) {
        if (animationType == LoadingButton.AnimationType.SUCCESSFUL)
            getActivity().finish();
    }
}
