package com.implementist.ireading.fragment;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.implementist.ireading.MyApplication;
import com.implementist.ireading.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends BaseFragment {

    EditText editId, editPassword;
    Button btnSubmit;
    TextView txtResult;

    @Override
    public int bindLayout() {
        return R.layout.fragment_favorites;
    }

    @Override
    public void initView(View view) {
        editId = $(R.id.editId);
        editPassword = $(R.id.editPassword);
        btnSubmit = $(R.id.btnSubmit);
        txtResult = $(R.id.txtResult);
    }

    @Override
    public void setListener() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetOnClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                VolleyPost();
                break;
        }
    }

    // 定义POST请求的方法
    private void VolleyPost() {
        //请求地址
        String url = "http://ireading.imwork.net/iReading/*";

        //取得请求队列
        RequestQueue requestQueue = MyApplication.getRequestQueue();

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET)
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
                            String result = jsonObject.getString("Result");
                            txtResult.setText(result);
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
                params.put("RequestType", "Login");
                params.put("Id", editId.getText().toString());
                params.put("Password", editPassword.getText().toString());
                return params;
            }
        };

        //将请求添加到队列中
        requestQueue.add(request);
    }
}
