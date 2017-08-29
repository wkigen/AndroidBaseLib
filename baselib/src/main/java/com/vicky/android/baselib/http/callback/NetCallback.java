package com.vicky.android.baselib.http.callback;

import com.vicky.android.baselib.mvvm.INetView;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by vicky on 2017/8/25.
 */
public abstract class NetCallback extends StringCallback {

    private INetView iView;

    public NetCallback(INetView iView){
        this.iView = iView;
    }
    @Override
    public void onBefore(Request request, int id)
    {
        if (iView != null)
            iView.showLoadingView(null);
    }

    @Override
    public void onAfter(int id)
    {
        if (iView != null)
            iView.dissmissLoadingView();
    }


}
