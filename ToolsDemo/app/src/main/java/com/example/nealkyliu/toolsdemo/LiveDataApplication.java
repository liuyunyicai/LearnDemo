package com.example.nealkyliu.toolsdemo;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModelStore;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.nealkyliu.toolsdemo.bussiness.test.compontent.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by nealkyliu on 2018/6/24.
 */

public class LiveDataApplication extends DaggerApplication implements ViewModelStoreOwner, LifecycleOwner {
    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
    ViewModelStore mViewModelStore;

    private static LiveDataApplication sInstance;

    public LiveDataApplication() {
        sInstance = this;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }


    public static Context getInst() {
        return sInstance;
    }


    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        if (null == mViewModelStore) {
            mViewModelStore = new ViewModelStore();
        }
        return mViewModelStore;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }
}
