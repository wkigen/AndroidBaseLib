package com.vicky.android.baselib.widget.viewloading;

public interface HttpNetLoadingViewHelper {

    /**
     * 加载中状态显示和隐藏,设置LoadingText
     */
    void showLoadingView();
    void dismissLoadingView();
    void setLoadingText(String loadingText);
    void clearView();
}
