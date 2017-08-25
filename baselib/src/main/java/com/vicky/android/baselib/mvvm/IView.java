package com.vicky.android.baselib.mvvm;

public interface IView extends INetView {

    void showErrorView();
    void showEmptyView();
    void showEmptyView(String content);
    void showDataView();

}
