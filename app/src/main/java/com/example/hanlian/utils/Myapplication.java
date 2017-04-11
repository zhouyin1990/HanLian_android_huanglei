package com.example.hanlian.utils;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class Myapplication extends Application {

    private OkHttpClient okHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();
        //配置OkHttp
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        //配置OKHttpUtils
        OkHttpUtils.initClient(okHttpClient);
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
