package com.vicky.android.baselib;

/**
 * Created by vicky on 2017/8/29.
 */
public class BaseApplication extends android.support.multidex.MultiDexApplication{

    private static BaseApplication mInstance;

    public static synchronized BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;


    }
}
