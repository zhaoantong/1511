package com.bwie.myokhttp.demo;


import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OKHttpUtil {
    //volatile 在多线程中保持OKHttpUtil对象的变量一致性
    private static volatile OKHttpUtil mInstance;
    private final OkHttpClient mOkHttpClient;
    private final Handler mHanlder;

    private OKHttpUtil() {
        mOkHttpClient = new OkHttpClient();
        mHanlder = new Handler();
    }

    /**
     * synchronized同步代码块进行双重验证 在多线程中保存对象唯一性
     *
     * @return
     */
    public static OKHttpUtil getInstance() {
        if (null == mInstance) {
            synchronized (OKHttpUtil.class) {
                if (null == mInstance) {
                    mInstance = new OKHttpUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * Get的异步请求
     *
     * @param url
     * @param paramsMap url http://apicloud.mob.com/v1/weather/type ?
     *                  HashMap key = 22ecf6c32440e
     *
     *    http://apicloud.mob.com/v1/weather/type ?    key = 22ecf6c32440e & key=22ecf6c32440e & key=22ecf6c32440e &key=22ecf6c32440e&
     */
    public void doGet(String url, HashMap<String, String> paramsMap, final OnResponseListener listener) {
        StringBuilder sb = new StringBuilder();
        //加上 字符串 : http://apicloud.mob.com/v1/weather/type
        sb.append(url);
        //sb字符串中不存在？拼接上？ 字符串：http://apicloud.mob.com/v1/weather/type ？
        if (sb.lastIndexOf("?") == -1) {
            sb.append("?");
        }//通过循环将参数给拼接起来  字符串： http://apicloud.mob.com/v1/weather/type ？ key = 22ecf6c32440e & key=22ecf6c32440e &
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        //字符串:http://apicloud.mob.com/v1/weather/type ？ key = 22ecf6c32440e & key=22ecf6c32440e  &
        //去掉字符串中最后的&字符 后 字符串:http://apicloud.mob.com/v1/weather/type ？ key = 22ecf6c32440e & key=22ecf6c32440e
        sb.deleteCharAt(sb.length() - 1);
        //通过Request的Builder对象设置请求方法（默认Get）设置请求地址 通过Build方法返回Request对象
        Log.i("t","doGet url ="+sb.toString());
        Request request = new Request.Builder().url(sb.toString()).build();
        //通过Call对象请求异步网络请求并返回数据回掉接口
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            //网络请求失败
            @Override
            public void onFailure(Call call, final IOException e) {
                //子线程转主线程
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        if (null != listener) {
                            //将数据设置到接口方法中
                            listener.onFailure(e.getMessage());
                        }
                    }
                });
            }

            //请求成功后返回服务器的响应结果
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                //子线程转主线程
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //返回成功
                            if (response.isSuccessful()) {
                                //通过响应体获取到响应内容
                                String jsonStr = response.body().string();
                                //将数据设置到接口方法中
                                if (null != listener) {
                                    //将数据设置到接口方法中
                                        listener.onSuccess(jsonStr);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }

    /**
     * 通过Post的异步请求
     *
     * @param url
     * @param paramsMap
     */
    public void doPost(String url, HashMap<String, String> paramsMap, final OnResponseListener listener) {
        FormBody.Builder builder = new FormBody.Builder();
        //通过循环将参数封装到form表单中
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            //名值对
            builder.add(entry.getKey(), entry.getValue());
        }
        //通过Request的builder对象设置请求方式和地址并返回Rquest对象
        Request request = new Request.Builder().post(builder.build()).url(url).build();
        //通过call对象进行异步请求
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            //网络请求失败信息
            @Override
            public void onFailure(Call call, final IOException e) {
                //将子线程转化为主线程 跨线程操作
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI主线程中执行
                        if (null != listener) {
                            //将数据设置到接口方法中
                            listener.onFailure(e.getLocalizedMessage());
                        }
                    }
                });
            }

            //网络请成功后服务器返回的响应结果
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                //将子线程转化为主线程 跨线程操作
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //判断是否返回成功
                            if (response.isSuccessful()) {
                                String jsonStr = response.body().string();
                                //UI主线程中执行
                                if (null != listener) {
                                    //将数据设置到接口方法中
                                    listener.onSuccess(jsonStr);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }

    /**
     * 通过Post进行文件异步上传
     *
     * @param url
     * @param paramsMap
     */
    public void doFileUpload(String url, HashMap<String, Object> paramsMap, final OnResponseListener listener) {
        //通过MultipartBody.Builder的对象封装参数
        MultipartBody.Builder builder = new MultipartBody.Builder();
        //设置表单提交
        builder.setType(MultipartBody.FORM);
        //循环设置参数
        for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {

            if (entry.getValue() instanceof String) {//判断对象类型是否一致 是否为String
                //将参数封装到表单体中
                builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
            } else if (entry.getValue() instanceof File) {//是否为File
                File file = (File) entry.getValue();
                //将参数封装到表单体中
                builder.addFormDataPart(entry.getKey(), file.getName(), MultipartBody.create(MultipartBody.FORM, file));
            }
        }
        //通过Request的Builder对象设置请求方式和地址并返回Request对象
        Request request = new Request.Builder().post(builder.build()).url(url).build();
        //通过Call对象进行网络异步请求并返回数据接口回掉
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            //网络请求失败
            @Override
            public void onFailure(Call call, final IOException e) {
                //子线程转主线程
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        if (null != listener) {
                            //将数据设置到接口方法中
                            listener.onFailure(e.getLocalizedMessage());
                        }
                    }
                });
            }

            //请求成功 服务器返回的响应
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                mHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //响应成功
                            if (response.isSuccessful()) {
                                //通过请求体获取到请求的结果
                                String jsonStr = response.body().string();
                                if (null != listener) {
                                    //将数据设置到接口方法中
                                    listener.onSuccess(jsonStr);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    /**
     * Get文件异步下载 参考doGet进程封装
     *
     * @param url
     * @param paramsMap
     */
    public void doFileDownload(String url, HashMap<String, String> paramsMap) {
        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    //数据返回的响应监听
    interface OnResponseListener {
        //成功
        public void onSuccess(String result);

        //失败
        public void onFailure(String result);
    }
}
