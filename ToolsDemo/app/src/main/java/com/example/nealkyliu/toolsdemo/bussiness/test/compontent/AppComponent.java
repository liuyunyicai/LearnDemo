package com.example.nealkyliu.toolsdemo.bussiness.test.compontent;

import android.content.Context;

import com.example.nealkyliu.toolsdemo.LiveDataApplication;
import com.example.nealkyliu.toolsdemo.bussiness.test.classed.InjectSetClass;
import com.example.nealkyliu.toolsdemo.bussiness.test.module.AppModule;
import com.example.nealkyliu.toolsdemo.bussiness.test.module.ContextModule;
import com.example.nealkyliu.toolsdemo.bussiness.test.module.StaticAppModule;

import java.util.Set;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/9
 **/
@Component(modules = {AppModule.class,
        AndroidSupportInjectionModule.class,
        StaticAppModule.class})
public interface AppComponent extends AndroidInjector<LiveDataApplication> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<LiveDataApplication> {}
}
