package com.vicky.android.baselib.widget.viewloading;

import android.content.Context;

import com.vicky.android.baselib.R;
import com.vicky.android.baselib.widget.LoadingBox;

public class BaseLoadingViewHelper implements HttpNetLoadingViewHelper {
    LoadingBox loadingBox;
    int numberOfRequest = 0;

    public BaseLoadingViewHelper(Context context){
        init(context);
    }

    public void init(Context context) {
        loadingBox = new LoadingBox(context);
        loadingBox.setCanceledOnTouchOutside(false);
    }

    public void setLoadingText(String loadingText){
        loadingBox.setText(loadingText);
    }
    @Override
    public void showLoadingView() {
        if(numberOfRequest == 0){
            try {
                loadingBox.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        numberOfRequest++;
    }

    @Override
    public void dismissLoadingView() {
        numberOfRequest--;
        if(numberOfRequest<0)
            numberOfRequest = 0;
        if(numberOfRequest == 0&&loadingBox.isShowing()){
            try {
                loadingBox.dismiss();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void clearView() {
        if(loadingBox!=null&&loadingBox.isShowing())
            loadingBox.dismiss();
    }

}
