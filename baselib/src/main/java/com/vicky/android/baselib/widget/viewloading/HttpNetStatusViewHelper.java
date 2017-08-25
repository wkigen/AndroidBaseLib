package com.vicky.android.baselib.widget.viewloading;

import android.view.View;

public interface HttpNetStatusViewHelper {

    /**
     * 加载中状态显示和隐藏,设置LoadingText
     */
    void showLoadingView();
    void dismissLoadingView();
    void setLoadingText(String loadingText);

    /**
     * 数据错误时候会将穿进来的dataview替换成errorview
     */
    void showNetErrorView();
    void setNeedNetErrorView(boolean netErrorView);

    /**
     * 数据为空的时候会将你传进来的dataview替换成emptyview
     */
    void showEmptyView();
    void setNeedNetEmptyView(boolean needNetEmpty);

    void clearView();

    /**
     * 绑定你的数据显示view,当网络状态改变时候，这个view会被其他对应状态的view替换
     * @param dataView
     */
    void bindDataView(View dataView);
    void showDataView();

    void setOnRefreshListener(OnNetStatuChangeListener onRefreshListener);
}
