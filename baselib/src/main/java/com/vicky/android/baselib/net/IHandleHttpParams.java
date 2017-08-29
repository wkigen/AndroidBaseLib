package com.vicky.android.baselib.net;

import java.util.Map;

/**
 * Created by vicky on 2017/8/29.
 */
public interface IHandleHttpParams {

    Map<String,String> handleParams(Map<String,String> params);
}
