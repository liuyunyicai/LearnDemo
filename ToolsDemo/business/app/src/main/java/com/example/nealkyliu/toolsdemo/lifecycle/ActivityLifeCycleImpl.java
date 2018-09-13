package com.example.nealkyliu.toolsdemo.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.example.nealkyliu.toolsdemo.utils.DebugLogger;

/**
 * @author: Created By nealkyliu
 * @date: 2018/8/30
 **/
public class ActivityLifeCycleImpl implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        DebugLogger.d(this, activity, "onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        DebugLogger.d(this, activity, "onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        DebugLogger.d(this, activity, "onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        DebugLogger.d(this, activity, "onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        DebugLogger.d(this, activity, "onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        DebugLogger.d(this, activity, "onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        DebugLogger.d(this, activity, "onActivityDestroyed");
    }
}
