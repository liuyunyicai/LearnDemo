package com.example.nealkyliu.toolsdemo.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;

import static android.arch.lifecycle.Lifecycle.State.STARTED;

/**
 * Created by nealkyliu on 2018/7/1.
 */

public class MyLocationListener implements LifecycleObserver {
    private boolean enabled = false;
    private Lifecycle mLifecycle;
    private IMyLocationCallback mCallback;
    public MyLocationListener(Context context, Lifecycle lifecycle, IMyLocationCallback callback) {
        mLifecycle = lifecycle;
        mCallback = callback;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        if (enabled) {
            // connect
        }
    }

    public void enable() {
        enabled = true;
        if (mLifecycle.getCurrentState().isAtLeast(STARTED)) {
            // connect if not connected
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop() {
        // disconnect if connected
    }

    interface IMyLocationCallback {
        void update();
    }
}