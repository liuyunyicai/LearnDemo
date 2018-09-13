package com.example.nealkyliu.toolsdemo.livedata;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by nealkyliu on 2018/6/24.
 */

public class ExtendedViewModelProviders extends ViewModelProviders {

    @NonNull
    @MainThread
    public static ViewModelProvider of(@NonNull Application application) {
        return of(application, null);
    }

    @NonNull
    @MainThread
    public static ViewModelProvider of(@NonNull Application application,
                                       @Nullable ViewModelProvider.Factory factory) {
        if (factory == null) {
            factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application);
        }
        return new ViewModelProvider(ExtendedViewModelStores.of(application), factory);
    }
}
