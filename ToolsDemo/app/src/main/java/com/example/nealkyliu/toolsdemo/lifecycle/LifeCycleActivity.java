package com.example.nealkyliu.toolsdemo.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.nealkyliu.toolsdemo.R;
import com.example.nealkyliu.toolsdemo.utils.LogUtils;

/**
 * Created by nealkyliu on 2018/7/1.
 */

public class LifeCycleActivity extends AppCompatActivity {

    public static class MyObserver implements LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void connectListener() {
            LogUtils.d(this, "connectListener");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void disconnectListener() {
            LogUtils.d(this, "disconnectListener");
        }
    }


    private MyLocationListener myLocationListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);

        getLifecycle().addObserver(new MyObserver());

        myLocationListener = new MyLocationListener(this, getLifecycle(), new MyLocationListener.IMyLocationCallback() {
            @Override
            public void update() {

            }
        });
    }
}
