package com.vicky.android.baselib.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vicky.android.baselib.R;

/**
 * Created by vicky on 2017/8/31.
 */
public class LoadingBox {

    private Context context;
    private Dialog dialog;
    private LinearLayout llLoading;
    private ImageView imLoading;
    private TextView tvText;
    public Animation animation;

    private boolean canceledOntouchOutside = true;

    public LoadingBox(Context con) {
        this.context = con;
        init();
    }

    public void init(){
        dialog = new Dialog(context, R.style.translucentDialogStyle);
        Window window = dialog.getWindow();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCanceledOnTouchOutside(canceledOntouchOutside);

        llLoading = (LinearLayout) dialog.findViewById(R.id.ll_loading);
        imLoading = (ImageView) dialog.findViewById(R.id.iv_loading);
        tvText = (TextView) dialog.findViewById(R.id.tv_text);
        animation = getAnimation();

    }

    private Animation getAnimation() {
        Animation animation = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setInterpolator(new LinearInterpolator());//不停顿
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(-1);
        return animation;
    }

    public void setText(String text){
        tvText.setText(text);
    }

    public void show() {
        try {
            dialog.show();
            imLoading.startAnimation(animation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismiss() {
        try {
            imLoading.clearAnimation();
        }catch (Exception e){}
        dialog.dismiss();
    }

    public boolean isShowing() {
        if (dialog.isShowing()) {
            return true;
        }
        return false;
    }

    public void setCanceledOnTouchOutside(boolean bool) {
        dialog.setCanceledOnTouchOutside(bool);
    }
}
