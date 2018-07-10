package com.example.nealkyliu.toolsdemo.livedata;

import android.app.Application;
import android.arch.lifecycle.ViewModelStore;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.arch.lifecycle.ViewModelStores;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by nealkyliu on 2018/6/24.
 */

public class ExtendedViewModelStores{

    /**
     * Returns the {@link ViewModelStore} of the given activity.
     *
     * @param application an application whose {@code ViewModelStore} is requested
     * @return a {@code ViewModelStore}
     */
    @NonNull
    @MainThread
    public static ViewModelStore of(@NonNull Application application) {
        if (application instanceof ViewModelStoreOwner) {
            return ((ViewModelStoreOwner) application).getViewModelStore();
        }
        return new ViewModelStore();
    }

    /**
     * Returns the {@link ViewModelStore} of the given activity.
     *
     * @param activity an activity whose {@code ViewModelStore} is requested
     * @return a {@code ViewModelStore}
     */
    @NonNull
    @MainThread
    public static ViewModelStore of(@NonNull FragmentActivity activity) {
        return ViewModelStores.of(activity);
    }

    /**
     * Returns the {@link ViewModelStore} of the given fragment.
     *
     * @param fragment a fragment whose {@code ViewModelStore} is requested
     * @return a {@code ViewModelStore}
     */
    @NonNull
    @MainThread
    public static ViewModelStore of(@NonNull Fragment fragment) {
        return ViewModelStores.of(fragment);
    }

}
