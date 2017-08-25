package com.vicky.android.baselib.widget.viewloading;

import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vicky.android.baselib.R;

/**
 * 功能：帮助切换错误，数据为空，正在加载的页面
 */
public class VaryViewHelper {
    /**
     * 切换不同视图的帮助类
     */
    OverlapViewHelper mViewHelper;
    /**
     * 错误页面
     */
    View mErrorView;

    /**
     * 数据为空的页面
     */
    View mEmptyView;



    public VaryViewHelper(View view) {
        this(new OverlapViewHelper(view));
    }

    public VaryViewHelper(OverlapViewHelper helper) {
        this.mViewHelper = helper;
    }


    void setUpEmptyView(View view) {
        mEmptyView = view;
        mEmptyView.setClickable(true);
    }

    void setUpErrorView(View view, View.OnClickListener listener) {
        mErrorView = view;
        mErrorView.setClickable(true);

        View btn = view.findViewById(R.id.vv_error_refresh);
        if (btn != null) {
            btn.setOnClickListener(listener);
        }
    }

    private void setmEmptyViewContent(String content){
        if(mEmptyView!=null){
            TextView textView = (TextView) mEmptyView.findViewById(R.id.empty_tips_show);
            textView.setText(content);
        }
    }

    public void setEmptyViewIco(@DrawableRes int resId){
        if(mEmptyView!=null){
            ImageView imageView = (ImageView) mEmptyView.findViewById(R.id.im_show_ico);
            imageView.setImageResource(resId);
        }
    }


    public void setEmptyViewBtn(String content,View.OnClickListener listener){
        if(mEmptyView!=null){
            TextView textView = (TextView) mEmptyView.findViewById(R.id.tv_do_some);
            textView.setVisibility(View.VISIBLE);
            textView.setText(content);
            textView.setOnClickListener(listener);
        }
    }

    public void showEmptyView(String content){
        setmEmptyViewContent(content);
        mViewHelper.showCaseLayout(mEmptyView);
    }


    public void showEmptyView() {
        setmEmptyViewContent("查询无结果");
        mViewHelper.showCaseLayout(mEmptyView);
    }

    public void showErrorView() {
        mViewHelper.showCaseLayout(mErrorView);
    }


    public void showDataView() {
        mViewHelper.restoreLayout();
    }



    public void releaseVaryView() {
        mErrorView = null;
        mEmptyView = null;
        mViewHelper.release();
        mViewHelper = null;
    }

    public static class Builder {
        private View mErrorView;
        private View mLoadingView;
        private View mEmptyView;
        private View mDataView;
        private View.OnClickListener mRefreshListener;

        public Builder setErrorView(View errorView) {
            mErrorView = errorView;
            return this;
        }

        public Builder setLoadingView(View loadingView) {
            mLoadingView = loadingView;
            return this;
        }

        public Builder setEmptyView(View emptyView) {
            mEmptyView = emptyView;
            return this;
        }

        public Builder setDataView(View dataView) {
            mDataView = dataView;
            return this;
        }

        public Builder setRefreshListener(View.OnClickListener refreshListener) {
            mRefreshListener = refreshListener;
            return this;
        }

        public VaryViewHelper build() {
            VaryViewHelper helper = new VaryViewHelper(mDataView);
            if (mEmptyView != null) {
                helper.setUpEmptyView(mEmptyView);
            }
            if (mErrorView != null) {
                helper.setUpErrorView(mErrorView, mRefreshListener);
            }

            return helper;
        }
    }

}
