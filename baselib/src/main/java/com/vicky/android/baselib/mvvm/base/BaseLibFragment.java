package com.vicky.android.baselib.mvvm.base;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;

import com.vicky.android.baselib.mvvm.AbstractViewModel;
import com.vicky.android.baselib.mvvm.IView;
import com.vicky.android.baselib.widget.viewloading.VaryViewHelper;

import butterknife.ButterKnife;

public abstract class BaseLibFragment<T extends IView, R extends AbstractViewModel<T>> extends ViewModelBaseFragment<T, R> {

    private static final int SHOW_LOADING = 0;
    private static final int DISMISS_LOADING = 1;

    protected Context context = null;
    protected Activity activity = null;

    Handler nethandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_LOADING:
                    try {
                        String text = null;
                        if (msg.obj == null) {
                            text = getString(com.vicky.android.baselib.R.string.lib_loading);
                        } else {
                            text = (String) msg.obj;
                        }
                        getLoadingViewHelper().setLoadingText(text);
                        getLoadingViewHelper().showLoadingView();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case DISMISS_LOADING:
                    try {
                        getLoadingViewHelper().dismissLoadingView();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void injectView(View mRootView) {
        ButterKnife.bind(this, mRootView);
        context = getActivity();
        activity = getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getLoadingViewHelper().clearView();
    }

    public void showLoadingView(String showText) {
        try {
            Message message = Message.obtain();
            message.what = SHOW_LOADING;
            message.obj = showText;
            nethandler.sendMessage(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void dissmissLoadingView() {
        try {
            nethandler.sendEmptyMessage(DISMISS_LOADING);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showErrorView() {

        try {
            mVaryViewHelper.showErrorView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showEmptyView() {
        try {
            mVaryViewHelper.showEmptyView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setEmptyViewIco(@DrawableRes int resId){
        try {
            mVaryViewHelper.setEmptyViewIco(resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setEmptyViewBtn(String content,View.OnClickListener listener){
        try {
            mVaryViewHelper.setEmptyViewBtn(content, listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showEmptyView(String content) {
        try {
            mVaryViewHelper.showEmptyView(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDataView() {
        try {
            mVaryViewHelper.showDataView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDataView(View dataView) {
        if (mVaryViewHelper != null) {
            mVaryViewHelper.showDataView();
            mVaryViewHelper.releaseVaryView();
        }
        mVaryViewHelper = new VaryViewHelper.Builder()
                .setDataView(dataView)
                .setEmptyView(LayoutInflater.from(mContext).inflate(com.vicky.android.baselib.R.layout.layout_emptyview, null))
                .setErrorView(LayoutInflater.from(mContext).inflate(com.vicky.android.baselib.R.layout.layout_errorview, null))
                .setRefreshListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRetryListener();
                    }
                })
                .build();
    }

}
