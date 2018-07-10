package com.example.nealkyliu.toolsdemo.bussiness.test.compontent;

import android.content.Context;

import com.example.nealkyliu.toolsdemo.LiveDataApplication;
import com.example.nealkyliu.toolsdemo.bussiness.test.module.ContextModule;

import dagger.Component;
import dagger.android.AndroidInjector;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/9
 **/
@Component(modules = {ContextModule.class})
public interface AppComponent extends AndroidInjector<LiveDataApplication> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<LiveDataApplication> {}
}
