package com.vicky.android.baselib.mvvm.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.vicky.android.baselib.mvvm.IViewModelProvider;
import com.vicky.android.baselib.mvvm.ViewModelProvider;


/**
 * All your activities must extend this activity - even in case your activity has no viewmodel. The fragment viewmodels are using the {@link IViewModelProvider}
 * interface to get the {@link ViewModelProvider} from the current activity.
 * You can copy this implementation in case you don't want to extend this class.
 */
public abstract class ViewModelBaseEmptyActivity extends AppCompatActivity implements IViewModelProvider {

    private ViewModelProvider mViewModelProvider;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        //This code must be execute prior to super.onCreate()
        mViewModelProvider = ViewModelProvider.newInstance(this);
        super.onCreate(savedInstanceState);
    }


    @Override
    @Nullable
    public final Object onRetainCustomNonConfigurationInstance() {
        return mViewModelProvider;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isFinishing()) {
            mViewModelProvider.removeAllViewModels();
        }
    }

    @Override
    public final ViewModelProvider getViewModelProvider() {
        return mViewModelProvider;
    }

}
