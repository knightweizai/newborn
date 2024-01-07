package com.zaozhuang.newborn.ui.activity;

import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zaozhuang.newborn.GlobalApplication;
import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.ui.base.BaseActivity;

import mangogo.appbase.util.LogUtil;

public class VolleyTestActivity extends BaseActivity {

    TextView tv_request;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_volley_test;
    }

    @Override
    protected void initView() {
        tv_request = findViewById(R.id.tv_request);

    }

    @Override
    protected void initListener() {
        tv_request.setOnClickListener(v -> {

            RequestQueue requestQueue = Volley.newRequestQueue(GlobalApplication.getGlobalContext());

            StringRequest request = new StringRequest(Request.Method.GET, "https://www.baidu.com", new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    LogUtil.d("TAG",response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            requestQueue.add(request);


        });


    }
}
