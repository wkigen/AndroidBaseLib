package com.vicky.android.baselib.widget.viewloading;

import android.content.Context;

import com.vicky.android.baselib.R;
import com.vicky.android.baselib.widget.TipDialog;

public class BaseLoadingViewHelper implements HttpNetLoadingViewHelper {
    TipDialog mTipdialog;
    int numberOfRequest = 0;

    public BaseLoadingViewHelper(Context context){
        init(context);
    }

    public void init(Context context) {
        mTipdialog = new TipDialog(context, TipDialog.TYPE_PB);
        mTipdialog.setTipText(context.getString(R.string.lib_loading));
        mTipdialog.setCanceledOnTouchOutside(false);
    }

    public void setLoadingText(String loadingText){
        mTipdialog.setTipText(loadingText);
    }
    @Override
    public void showLoadingView() {
        if(numberOfRequest == 0){
            try {
                mTipdialog.show();
                mTipdialog.setTagIvAnimation();
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
        if(numberOfRequest == 0&&mTipdialog.isShowing()){
            try {
                mTipdialog.dismiss();
                mTipdialog.cancleAnimation();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void clearView() {
        if(mTipdialog!=null&&mTipdialog.isShowing())
            mTipdialog.dismiss();
    }

}
