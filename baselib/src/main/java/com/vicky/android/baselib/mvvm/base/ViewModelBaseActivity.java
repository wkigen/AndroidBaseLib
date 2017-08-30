package com.vicky.android.baselib.mvvm.base;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.TransitionRes;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.vicky.android.baselib.R;
import com.vicky.android.baselib.ActivityManager;
import com.vicky.android.baselib.utils.StatusBarUtil;
import com.vicky.android.baselib.mvvm.AbstractViewModel;
import com.vicky.android.baselib.mvvm.IView;
import com.vicky.android.baselib.mvvm.ViewModelHelper;
import com.vicky.android.baselib.runtimepermission.PermissionsManager;
import com.vicky.android.baselib.widget.viewloading.BaseLoadingViewHelper;
import com.vicky.android.baselib.widget.viewloading.HttpNetLoadingViewHelper;
import com.vicky.android.baselib.widget.viewloading.VaryViewHelper;


@SuppressWarnings("all")
public abstract class ViewModelBaseActivity<T extends IView, RM extends AbstractViewModel<T>> extends ViewModelBaseEmptyActivity implements IView {

    private final ViewModelHelper<T, RM> mViewModeHelper = new ViewModelHelper<>();
    protected Context mContext;
    protected VaryViewHelper mVaryViewHelper;
    protected HttpNetLoadingViewHelper httpNetLoadingViewHelper;

    @TransitionRes
    protected int enterTransition,exitTransition ,reenterTransition;

    public HttpNetLoadingViewHelper getLoadingViewHelper() {
        if(httpNetLoadingViewHelper == null){
            httpNetLoadingViewHelper = new BaseLoadingViewHelper(this);
        }
        return httpNetLoadingViewHelper;
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModeHelper.onCreate(this, savedInstanceState, getViewModelClass(), getIntent().getExtras());
        mContext = this;

        activityTransition();

        setContentView(tellMeLayout());
        ActivityManager.getAppManager().addActivity(this);
        //bundle
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }
        //initView ok
        initView(savedInstanceState);
        //varyView
        if (getStatusTargetView() != null) {
            mVaryViewHelper = new VaryViewHelper.Builder()
                    .setDataView(getStatusTargetView())
                    .setEmptyView(LayoutInflater.from(mContext).inflate(R.layout.layout_emptyview, null))
                    .setErrorView(LayoutInflater.from(mContext).inflate(R.layout.layout_errorview, null))
                    .setRefreshListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onRetryListener();
                        }
                    })
                    .build();
        }

        setModelView((T)this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if (isBackLayout()) {
            super.setContentView(tellMeLayout());
        } else {
            super.setContentView(layoutResID);
        }
    }

    /**
     * need drag back
     */
    protected boolean isBackLayout() {
        return false;
    }

    protected abstract void getBundleExtras(@NonNull Bundle extras);

    protected void setStatusBar() {
        StatusBarUtil.setTranslucent(this);
    }

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract int tellMeLayout();

    protected void setActivityTransition(){};

    @TargetApi(21)
    protected void activityTransition(){

        setActivityTransition();

        if (enterTransition != 0 || exitTransition != 0 || reenterTransition!=0)
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        if (enterTransition > 0){
            Transition enterT = TransitionInflater.from(this).inflateTransition(enterTransition);
            getWindow().setEnterTransition(enterT);
        }

        if (exitTransition > 0){
            Transition exitT = TransitionInflater.from(this).inflateTransition(exitTransition);
            getWindow().setExitTransition(exitT);
        }

        if (reenterTransition > 0){
            Transition reenterT = TransitionInflater.from(this).inflateTransition(reenterTransition);
            getWindow().setReenterTransition(reenterT);
        }
    }

    /**
     * Rewrite this method defines the network status view
     * @return
     */
    protected abstract View getStatusTargetView();

    /**
     * when set getStatusTargetView,use under error status
     */
    protected void onRetryListener() {

    }
    /**
     * Call this after your view is ready - usually on the end of {@link android.app.Activity#onCreate(Bundle)}
     *
     * @param view view
     */
    @SuppressWarnings("unused")
    public final void setModelView(@NonNull final T view) {
        mViewModeHelper.setView(view);
    }

    public abstract Class<RM> getViewModelClass();

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        mViewModeHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModeHelper.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewModeHelper.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModeHelper.onDestroy(this);
        if (mVaryViewHelper != null) mVaryViewHelper.releaseVaryView();
        ActivityManager.getAppManager().popActivity(this);
    }

    /**
     * @see ViewModelHelper#getViewModel()
     */
    @SuppressWarnings("unused")
    @NonNull
    public final RM getViewModel() {
        return mViewModeHelper.getViewModel();
    }

    @SuppressWarnings("all")
    protected final <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    /**
     * startActivity
     */
    final public void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     */
    final public void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     */
    final public void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     */
    final public void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     */
    final public void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     */
    final public void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    final public void readyGoForTransitionAnimation(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}
