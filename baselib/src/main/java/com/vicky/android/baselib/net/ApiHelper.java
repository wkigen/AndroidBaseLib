package com.vicky.android.baselib.net;

import android.content.Context;

import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * Created by vicky on 2017/8/25.
 */
public class ApiHelper {
    public static <T> T get(Class<T> c,IHandleHttpParams handleHttpParams,Map<String,String> headMap) {
        return (T) Proxy.newProxyInstance(
                c.getClassLoader(),
                new Class[]{c},
                new NetUtil(handleHttpParams,headMap));
    }
}
