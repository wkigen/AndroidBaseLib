package com.vicky.android.baselib.widget;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;


public class PopupWindowEx extends PopupWindow {
    Activity activity;

    public PopupWindowEx(Activity activity){
        super(activity);
        this.activity = activity;
        setOnDismissListener();
    }

    public PopupWindowEx(Activity activity, View contentView, int width, int height) {
        this(contentView, width, height);
        this.activity = activity;
    }

    private PopupWindowEx(View contentView, int width, int height) {
        super(contentView, width, height);
        setOnDismissListener();
    }

    OnDismissListener onDismissListener;

    private void setOnDismissListener(){
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                ValueAnimator anim = ValueAnimator.ofFloat(0.6f, 1f);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if (activity == null) return;
                        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                        lp.alpha = (float) animation.getAnimatedValue();
                        activity.getWindow().setAttributes(lp);
                    }
                });
                anim.setDuration(300);
                anim.start();
                if(onDismissListener!=null)onDismissListener.onDismiss();
            }
        });
    }

    public void setOnYDDismissListener( OnDismissListener dismissListener){
        this.onDismissListener = dismissListener;
    }


    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void showAsDropDown(View anchor, int xoff, int yoff){
        beginBgAnimation();
        super.showAsDropDown(anchor, xoff, yoff);
    }

    public void showAtLocation(View parent, int gravity, int x, int y,boolean animation) {
        if(animation)
        beginBgAnimation();
        super.showAtLocation(parent, gravity, x, y);
    }

    public void showAtLocation(View parent, int gravity, int x, int y){
        showAtLocation(parent,gravity,x,y,true);
    }

    public void beginBgAnimation(){
        if (activity != null) {
            ValueAnimator anim = ValueAnimator.ofFloat(1.0f, 0.6f);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                    lp.alpha = (float) animation.getAnimatedValue();
                    activity.getWindow().setAttributes(lp);
                }
            });
            anim.setDuration(300);
            anim.start();
        }
    }

}
