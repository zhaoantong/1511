package com.bwie.myokhttp.demo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.HashMap;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void doGetRequest(View view) {
        //"http://apicloud.mob.com/v1/weather/type?key=22ecf6c32440e"
        HashMap<String, String> parasMap = new HashMap<>();
        parasMap.put("key", "22ecf6c32440e");
        OKHttpUtil.getInstance().doGet("http://apicloud.mob.com/v1/weather/type", parasMap, new OKHttpUtil.OnResponseListener() {
            @Override
            public void onSuccess(String result) {
                Log.i("t", "doGet onSuccess result=" + result);
            }

            @Override
            public void onFailure(String result) {
                Log.i("t", "doGet onFailure result=" + result);
            }
        });
    }

    public void doPostRequest(View view) {
        HashMap<String, String> parasMap = new HashMap<>();
        parasMap.put("mobile", "17611200379");
        parasMap.put("password", "123456");
        OKHttpUtil.getInstance().doPost("http://120.27.23.105/user/login", parasMap, new OKHttpUtil.OnResponseListener() {
            @Override
            public void onSuccess(String result) {
                Log.i("t", "doPost onSuccess result=" + result);
            }

            @Override
            public void onFailure(String result) {
                Log.i("t", "doPost onFailure result=" + result);
            }
        });
    }

    public void doFileUpload(View view) {
        String uid = "11972";
        //sdcard路径+"/"+文件名
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/" + "img.png";
        File file = new File(filePath);
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("uid", uid);
        paramsMap.put("file", file);
        OKHttpUtil.getInstance().doFileUpload("https://www.zhaoapi.cn/file/upload", paramsMap, new OKHttpUtil.OnResponseListener() {
            @Override
            public void onSuccess(String result) {
                Log.i("t", "doFileUpload onSuccess result=" + result);
            }

            @Override
            public void onFailure(String result) {
                Log.i("t", "doFileUpload onFailure result=" + result);
            }
        });
    }

    public void doFileDownload(View view) {
    }
}
