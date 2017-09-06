package com.vicky.android.baselib.http.callback;

import com.vicky.android.baselib.mvvm.INetView;
import com.vicky.android.baselib.mvvm.IView;

import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public abstract class Callback<T>
{
    protected INetView iView;

    public void showProgress(INetView iView){
        this.iView = iView;
    }

    /**
     * UI Thread
     *
     * @param request
     */
    public void onBefore(Request request, int id)
    {
        if (iView != null)
            iView.showLoadingView(null);
    }

    /**
     * UI Thread
     *
     * @param
     */
    public void onAfter(int id)
    {
        if (iView != null)
            iView.dissmissLoadingView();
    }

    /**
     * UI Thread
     *
     * @param progress
     */
    public void inProgress(float progress, long total , int id)
    {

    }

    /**
     * Thread Pool Thread
     *
     * @param
     */
    public void readData(byte[] buf,int len,long sum){

    }

    /**
     * if you parse reponse code in parseNetworkResponse, you should make this method return true.
     *
     * @param response
     * @return
     */
    public boolean validateReponse(Response response, int id)
    {
        return response.isSuccessful();
    }

    /**
     * Thread Pool Thread
     *
     * @param response
     */
    public abstract T parseNetworkResponse(Response response, int id) throws Exception;

    public abstract void onError(Call call, Exception e, int id);

    public abstract void onResponse(T response, int id);


    public static Callback CALLBACK_DEFAULT = new Callback()
    {

        @Override
        public Object parseNetworkResponse(Response response, int id) throws Exception
        {
            return null;
        }

        @Override
        public void onError(Call call, Exception e, int id)
        {

        }

        @Override
        public void onResponse(Object response, int id)
        {

        }
    };

}