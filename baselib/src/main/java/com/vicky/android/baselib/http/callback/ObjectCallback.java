package com.vicky.android.baselib.http.callback;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by vicky on 2016/11/3.
 */
public abstract class ObjectCallback<T> extends Callback<T>
{
    @Override
    public T parseNetworkResponse(Response response, int id) throws IOException
    {
        String string = response.body().string();
        Object object =  JSON.parse(string);
        return (T)object;
    }
}