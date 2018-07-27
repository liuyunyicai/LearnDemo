package com.example.nealkyliu.toolsdemo;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModelStore;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import com.example.nealkyliu.toolsdemo.bussiness.config.AppConfigCenter;
import com.example.nealkyliu.toolsdemo.bussiness.test.classed.InjectSetClass;
import com.example.nealkyliu.toolsdemo.bussiness.test.compontent.DaggerAppComponent;
import com.example.nealkyliu.toolsdemo.room.AppDatabase;
import com.example.nealkyliu.toolsdemo.utils.Logger;

import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by nealkyliu on 2018/6/24.
 */

public class LiveDataApplication extends DaggerApplication implements ViewModelStoreOwner, LifecycleOwner {
    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
    ViewModelStore mViewModelStore;

    private static LiveDataApplication sInstance;

    private AppDatabase mAppSettingDatabase;

    public LiveDataApplication() {
        sInstance = this;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }

    @Inject
    Set<InjectSetClass> mSetClassed;

    @Inject
    Map<String, InjectSetClass> mStringMapClasses;

    @Inject
    Map<Long, InjectSetClass> mClassMapClasses;

    public static LiveDataApplication getInst() {
        return sInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        AppConfigCenter.init(this);
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

    public void test() {
        for (Map.Entry<String, InjectSetClass> entry : mStringMapClasses.entrySet()) {
            Logger.d(entry.getKey());
            entry.getValue().test();
        }

        for (Map.Entry<Long, InjectSetClass> entry : mClassMapClasses.entrySet()) {
            Logger.d(entry.getKey().toString());
            entry.getValue().test();
        }
    }

    public AppDatabase getAppDb() {
        if (null == mAppSettingDatabase) {
            mAppSettingDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "AppDatabase").build();
        }
        return mAppSettingDatabase;
    }
}
