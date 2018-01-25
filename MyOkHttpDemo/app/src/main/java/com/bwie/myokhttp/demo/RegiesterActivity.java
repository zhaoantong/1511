package com.bwie.myokhttp.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegiesterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mobileView;
    private EditText passwordView;
    /**
     * 注册
     */
    private Button mRegiesterView;
    /**
     * 登陆
     */
    private Button mLoginView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        initView();
    }

    private void initView() {
        mobileView = (EditText) findViewById(R.id.mobileView);
        passwordView = (EditText) findViewById(R.id.passwordView);
        mRegiesterView = (Button) findViewById(R.id.regiesterView);
        mRegiesterView.setOnClickListener(this);
        mLoginView = (Button) findViewById(R.id.loginView);
        mLoginView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.regiesterView) {
            doRegister();
        } else if (id == R.id.loginView) {
            doLogin();
        }
    }

    /**
     * 注册 post异步请求
     */
    private void doRegister() {
        String mobile = mobileView.getText().toString();
        String password = passwordView.getText().toString();
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("mobile", mobile);
        paramsMap.put("password", password);
        OKHttpUtil.getInstance().doPost("url", paramsMap, new OKHttpUtil.OnResponseListener() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFailure(String result) {

            }
        });
        //验证
        //自己实现
//        OkHttpClient okHttpClient = new OkHttpClient();
//        //表单申请 封装参数
//        FormBody.Builder builder = new FormBody.Builder().
//                add("mobile", mobile)
//                .add("password", password);
//        Request request = new Request.Builder().post(builder.build()).url("http://120.27.23.105/user/reg").build();
//        Call call = okHttpClient.newCall(request);
//        //异步请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String jsonStr = response.body().string();
//                    //解析json {"msg":"注册成功","code":"0"}
//                    final LoginBean bean = new Gson().fromJson(jsonStr, LoginBean.class);
//                    if (bean.getCode().equals("0")) {
//                        //将子线程转化到主线程
//                        RegiesterActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(RegiesterActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(RegiesterActivity.this, LoginActivity.class));
//                            }
//                        });
//                    }
//                }
//            }
//        });
    }

    private void doLogin() {
        startActivity(new Intent(RegiesterActivity.this, LoginActivity.class));
    }

}
