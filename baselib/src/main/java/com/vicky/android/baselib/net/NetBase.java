package com.vicky.android.baselib.net;


import com.vicky.android.baselib.http.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;
import com.vicky.android.baselib.http.OkHttpUtils;
import okhttp3.OkHttpClient;

/**
 * Created by vicky on 2017/8/25.
 */
public class NetBase {

    public static void init(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor(NetBase.class.getSimpleName()))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                        //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

}
