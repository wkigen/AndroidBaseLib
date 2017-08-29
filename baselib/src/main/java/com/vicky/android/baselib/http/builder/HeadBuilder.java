package com.vicky.android.baselib.http.builder;


import com.vicky.android.baselib.http.OkHttpUtils;
import com.vicky.android.baselib.http.request.OtherRequest;
import com.vicky.android.baselib.http.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
