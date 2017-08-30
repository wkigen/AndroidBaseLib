package com.vicky.android.baselib.mvvm.base;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

import com.vicky.android.baselib.R;
import com.vicky.android.baselib.utils.StatusBarUtil;
import com.vicky.android.baselib.mvvm.AbstractViewModel;
import com.vicky.android.baselib.mvvm.IView;
import com.vicky.android.baselib.widget.viewloading.VaryViewHelper;

import butterknife.ButterKnife;


public abstract class BaseLibActivity<T extends IView, RM extends AbstractViewModel<T>> extends ViewModelBaseActivity<T, RM> {

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
                            text = getString(R.string.lib_loading);
                        } else {
                            text = (String) msg.obj;
                        }
                        getLoadingViewHelper().setLoadingText(text);
                        getLoadingViewHelper().showLoadingView();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case DISMISS_LOADING:
                    getLoadingViewHelper().dismissLoadingView();
                    break;
            }
        }
    };


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        context = this;
        activity = this;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.base_lib_theme_red),0);
    }

    @Override
    public void onDestroy() {
        try {
            nethandler.removeCallbacksAndMessages(null);
            nethandler = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            getLoadingViewHelper().clearView();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            if (nethandler != null)
                nethandler.sendEmptyMessage(DISMISS_LOADING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showErrorView() {
        try {
            if (mVaryViewHelper != null)
                mVaryViewHelper.showErrorView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showEmptyView() {
        try {
            if (mVaryViewHelper != null)
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
            if (nethandler != null)
                mVaryViewHelper.showDataView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDataView(View dataView) {
        if (mVaryViewHelper != null)
            mVaryViewHelper.showDataView();
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
