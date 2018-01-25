package com.bwie.myokhttp.demo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    private EditText mobileView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * OKHttp的Get网络请求（同步）
     *
     * @param view
     */
    public void doGetRequest_bak(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //实例化OkHttpClient
                    OkHttpClient okHttpClient = new OkHttpClient();
                    //通过Request的builder对象设置Get请求的请求地址返回Request对象
                    Request request = new Request.Builder().url("http://apicloud.mob.com/v1/weather/type?key=22ecf6c32440e").build();
                    //通过newCall方法获取到Call对象
                    Call call = okHttpClient.newCall(request);
                    //通过call去进行网络请求并返回响应结果
                    Response response = call.execute();
                    //判断响应是否正确
                    if (response.isSuccessful()) {
                        //获取到响应体
                        ResponseBody responseBody = response.body();
                        //根据相应体获取到相应内容
                        String jsonStr = responseBody.string();
                        Log.i("t", "result=" + jsonStr);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * OKHttp的Get网络请求（异步）
     *
     * @param view
     */
    public void doGetRequest(View view) {
        //实例化
        OkHttpClient okHttpClient = new OkHttpClient();
        //通过Request的builder对象设置Get请求的请求地址返回Request对象
        //默认是get请求 ，get方法可以省略
        Request request = new Request.Builder().get().url("http://apicloud.mob.com/v1/weather/type?key=22ecf6c32440e").build();
        //通过newCall方法获取到Call对象
        Call call = okHttpClient.newCall(request);
        //通过call去进行网络请求队列并通过接口回掉返回响应结果
        //同步和异步的区别
        call.enqueue(new Callback() {
            //网络请求失败时提示
            @Override
            public void onFailure(Call call, IOException e) {

            }

            //网络请求成功后，服务器响应的结果
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //判断响应结果是否正确 200
                if (response.isSuccessful()) {
                    //InputStream inputStream=response.body().byteStream();
                    //byte[] bytes = response.body().bytes();
                    //根据响应体获取到响应内容
                    String jsonStr = response.body().string();
                    Log.i("t", "result=" + jsonStr);
                }
            }
        });
    }

    /**
     * OKHttp的Post网络请求（同步）
     *
     * @param view
     */
    public void doPostRequest_bak(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //实例化OkHttpClient
                    OkHttpClient okHttpClient = new OkHttpClient();
                    //区别----------------------------------------
                    //通过FormBody的builder方法获取到buildr对象
                    //FormBody.Builder builder = new FormBody.Builder();
                    //通过builder设置表单请求数据
                    //builder.add("", "");
                    FormBody.Builder builder = new FormBody.Builder()
                            .add("mobile", "17611200379")
                            .add("password", "123456");
                    //需要设置编码 中文编码时用到
                    //builder.addEncoded("","");
                    //通过builder对象的build方法获取到FormBody对象
                    //FormBody extends RequestBody
                    //FormBody body = builder.build();
                    //----------------------------------------
                    //通过Request的builder对象设置post请求方式并设置请求地址并返回请求对象request
                    //第一种
                    //Request request = new Request.Builder().post(body).url("").build();
                    // 第二种
                    Request request = new Request.Builder().post(builder.build()).url("http://120.27.23.105/user/login").build();
                    //通过调用newCall方法获取到Call对象
                    Call call = okHttpClient.newCall(request);
                    //通过call请求网络获取到响应对象Response
                    Response response = call.execute();
                    //判断响应结果是否正确 200
                    if (response.isSuccessful()) {
                        String josnStr = response.body().string();
                        Log.i("t", "result=" + josnStr);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * OKHttp的Post网络请求（异步）
     *
     * @param view
     */
    public void doPostRequest(View view) {
        //String mobile = mobileView.getText().toString();
        //String password = "123456";
        //判断
//        if (TextUtils.isEmpty(mobile) || mobile.length() == 0) {
//            Toast.makeText(getApplicationContext(), "手机号为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
        //手机号格式
        //------------
        //判断密码长度
        //---------------
        //实例化OkhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //通过Formbody的builder对象设置请求的参数
        FormBody.Builder builder = new FormBody.Builder()
                .add("mobile", "17611200379")
                .add("password", "123456");
//        FormBody.Builder builder = new FormBody.Builder()
//                .add("mobile", "17611200379")
//                .add("password", "123456");
        Request request = new Request.Builder().post(builder.build()).url("http://120.27.23.105/user/login").build();
        //通过newCall获取到Call对象
        Call call = okHttpClient.newCall(request);
        //通过Call对象调用enqueue进行异步请求并返回请求接口
        call.enqueue(new Callback() {
            //返回网络请求失败
            @Override
            public void onFailure(Call call, IOException e) {
            }

            //返回网络请求成功后服务器的响应结果
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //返回相应是否成功
                if (response.isSuccessful()) {
                    //Toast只能在UI主线程中执行，如果子线程中运行则会报错
                    // Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                    //通过响应体获取到响应内容
                    String jonsStr = response.body().string();
                    //Log.i("t", "异步 result=" + jonsStr);
                }
            }
        });
    }

    /**
     * OKHttp 的图片/文件 通过post上传
     * 1.相册选择图片获取图片路径
     * 2.通过摄像头获取到拍摄的图片地址
     * sdcard路径+"/"+文件名
     *
     * @param view
     */
    public void doFileUploadRequest(View view) {
        String uid = "11972";
        //sdcard路径+"/"+文件名
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/" + "img.png";
        File file = new File(filePath);
//        HashMap<String, Object> paramsMap = new HashMap<>();
//        paramsMap.put("uid", uid);
//        paramsMap.put("file", file);
//        OKHttpUtil.getInstance().doFileUpload("url", paramsMap);
        //实例化Okhttp的对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //----------------------------------------------------
        //FormBody 登陆、注册 普通的表单请求 用于字符串参数
        //用在文件的提交中 包含字符串和文件（图片、视频、其他的文件）
        MultipartBody.Builder builder = new MultipartBody.Builder();
        //指明通过Form表单进行提交 如果指明提交方式，数据提交不上去
        builder.setType(MultipartBody.FORM);
        //在请求体封装请求参数
        builder.addFormDataPart("uid", uid);
        //文件和视频都上传，上传文件的格式multipart/form-data和addFormDataPart 以表单数据格式FormData上传
        builder.addFormDataPart("file", file.getName(), MultipartBody.create(MultipartBody.FORM, file));
        //-----------------------------------------------------
        //通过Request的builder对象设置请求方式和请求的地址并返回Request对象
        Request request = new Request.Builder().post(builder.build()).url("https://www.zhaoapi.cn/file/upload").build();
        //通过newCall获取到call对象
        Call call = okHttpClient.newCall(request);
        //通过call对象进行异步网络请求并返回数据回掉接口 Callback在异步线程中
        call.enqueue(new Callback() {
            //网络请求失败
            @Override
            public void onFailure(Call call, IOException e) {

            }

            //网络请求成功后服务端返回的响应
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //响应成功
                if (response.isSuccessful()) {
                    //通过响应体获取到响应的内容json字符串
                    final String jsonStr = response.body().string();//toStirng()
                    //子线程中输出log
                    Log.i("t", "result=" + jsonStr);
                    //子线程转主线程
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //UI主线程中输出Toast提示
                            Toast.makeText(MainActivity.this, jsonStr, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    /**
     * OkHttp文件下载，通过Post/Get异步下载
     *
     * @param view
     */
    public void doFileDownloadRequest(View view) {
        String reqUrl = "https://storage.jd.com/jdmobile/JDMALL-PC2.apk";//"http://msoftdl.360.cn/mobilesafe/shouji360/360safe/500192/360MobileSafe.apk";
        String sdcardPath = Environment.getExternalStorageDirectory().getPath();
        String filePath = "jd.apk";//"360MobileSafe.apk";
        //final File file = new File(sdcardPath , filePath);
        final File file = new File(sdcardPath + "/" + filePath);
        //实例化okHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //通过Request对象设置请求方式和地址 默认是get请求
        Request request = new Request.Builder().url(reqUrl).build();
        //异步执行
        okHttpClient.newCall(request).enqueue(new Callback() {
            //网络请求失败
            @Override
            public void onFailure(Call call, IOException e) {

            }

            //网络请求成功后返回响应体
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //子线程中执行
                if (response.isSuccessful()) {
                    //文件在本地的保存
                    //-----------------------------------------------
                    InputStream inputStream = response.body().byteStream();
                    OutputStream outputStream = new FileOutputStream(file);
                    byte[] buffer = new byte[1024 * 3];//min 1024 -- max 1024*3
                    int len = 0;
                    int offset = 0;
                    //通过outputStream将文件以3072个字节写入到sdcard中
                    while ((len = inputStream.read(buffer)) != -1) {//0--3072 byte
                        //0--3072 byte
                        outputStream.write(buffer, 0, len);
                        offset += len;
                        Log.i("t", "文件下载：==" + offset);
                    }
                    outputStream.flush();
                    outputStream.close();
                    inputStream.close();
                    //--------------------------------------------------
                    //判断文件是否在本地sdcard上
                    if (file.exists()) {
                        Log.i("t", "文件已经存在，下载成功");
                    } else {
                        Log.i("t", "文件不存在");
                    }
                }
            }
        });
    }
}
