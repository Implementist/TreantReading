package com.implementist.ireading;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dx.dxloadingbutton.lib.LoadingButton;
import com.implementist.ireading.activity.LoginActivity;
import com.implementist.ireading.activity.RegisterActivity;
import com.implementist.ireading.fragment.BookListFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */

public class HttpRequestUtils {
    private static final String[] LOGIN_TYPE = {
            "LoginByPhoneNumber", "LoginByEmailAddress", "LoginByUserName"};

    public static final String SERVER_ROOT = "http://ireading.imwork.net/";

    /**
     * 注册第一步——检查手机号是否已注册
     *
     * @param button      Loading Button
     * @param activity    Register Activity
     * @param phoneNumber 手机号码
     */
    public static void RegisterStep1Request(final LoadingButton button,
                                            final RegisterActivity activity, final String phoneNumber) {
        //请求地址
        String url = HttpRequestUtils.SERVER_ROOT + "iReading/*";
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
                                button.loadingSuccessful();
                            } else {
                                button.loadingFailed();
                                activity.showToast("该手机号已经被注册，验证手机号后可直接登录");
                            }
                        } catch (JSONException e) {
                            activity.showToast("网络请求发生错误，稍后请重试");
                            button.loadingFailed();
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                activity.showToast("网络请求发生错误，稍后请重试");
                button.loadingFailed();
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("RequestType", "Register");
                params.put("RegisterType", "RegisterByPhoneNumber");
                params.put("RegisterStep", "1");
                params.put("PhoneNumber", phoneNumber);
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }

    /**
     * 注册第三步——设置密码
     *
     * @param button      Loading Button
     * @param activity    Register Activity
     * @param phoneNumber 手机号码
     * @param password    密码
     */
    public static void RegisterStep3Request(final LoadingButton button, final RegisterActivity activity,
                                            final String phoneNumber, final String password) {
        //请求地址
        String url = HttpRequestUtils.SERVER_ROOT + "iReading/*";
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
                                button.loadingSuccessful();
                            } else {
                                button.loadingFailed();
                                activity.showToast("服务器繁忙，请稍后重试");
                            }
                        } catch (JSONException e) {
                            activity.showToast("网络请求发生错误，稍后请重试");
                            button.loadingFailed();
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                activity.showToast("网络请求发生错误，稍后请重试");
                button.loadingFailed();
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("RequestType", "Register");
                params.put("RegisterType", "RegisterByPhoneNumber");
                params.put("RegisterStep", "3");
                params.put("PhoneNumber", phoneNumber);
                params.put("Password", password);
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }

    /**
     * 通过账号密码登录
     *
     * @param typeID        登录类型ID
     * @param button        Loading Button
     * @param activity      Login Activity
     * @param accountNumber 账号
     * @param password      密码
     */
    public static void LoginByPasswordRequest(final int typeID, final LoadingButton button,
                                              final LoginActivity activity, final String accountNumber,
                                              final String password) {
        //请求地址
        String url = HttpRequestUtils.SERVER_ROOT + "iReading/*";
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
                                button.loadingSuccessful();
                            } else {
                                activity.showToast("账号或密码错误，请重新输入");
                                button.loadingFailed();
                            }
                        } catch (JSONException e) {
                            activity.showToast("网络请求发生错误，稍后请重试");
                            button.loadingFailed();
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                activity.showToast("网络请求发生错误，稍后请重试");
                button.loadingFailed();
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("RequestType", "Login");
                params.put("LoginType", LOGIN_TYPE[typeID]);
                params.put("AccountNumber", accountNumber);
                params.put("Password", password);
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }

    /**
     * 查询所有书籍
     */
    public static void SearchAllBooksRequest() {
        //请求地址
        String url = HttpRequestUtils.SERVER_ROOT + "iReading/*";
        String tag = "SearchAllBooks";

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
                            MyApplication.books = jsonObject.getJSONArray("Books");
                            BookListFragment.initView();
                        } catch (JSONException e) {
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("RequestType", "SearchAllBooks");
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }
}
