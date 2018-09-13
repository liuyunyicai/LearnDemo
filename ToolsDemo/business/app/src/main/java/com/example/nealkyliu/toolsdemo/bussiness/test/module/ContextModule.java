package com.example.nealkyliu.toolsdemo.bussiness.test.module;

import android.content.Context;

import com.example.nealkyliu.toolsdemo.LiveDataApplication;
import com.example.nealkyliu.toolsdemo.bussiness.test.scope.ContextLife;

import dagger.Module;
import dagger.Provides;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/8
 **/
@Module
public class ContextModule {

    @ContextLife("Activity")
    @Provides
    Context getActivityContext() {
        return LiveDataApplication.getInst();
    }

    @ContextLife("Application")
    @Provides
    Context getApplicationContext() {
        return LiveDataApplication.getInst();
    }

}
