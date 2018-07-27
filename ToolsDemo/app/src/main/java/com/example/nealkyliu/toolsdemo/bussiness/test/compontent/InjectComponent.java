package com.example.nealkyliu.toolsdemo.bussiness.test.compontent;

import com.example.nealkyliu.toolsdemo.bussiness.test.classed.AbsInjectClass;
import com.example.nealkyliu.toolsdemo.bussiness.test.classed.IAbsClass;
import com.example.nealkyliu.toolsdemo.bussiness.test.classed.IInjectClass;
import com.example.nealkyliu.toolsdemo.bussiness.test.job.TestJobImpl;
import com.example.nealkyliu.toolsdemo.bussiness.test.module.ContextModule;
import com.example.nealkyliu.toolsdemo.bussiness.test.module.InjectModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/6
 **/
@Singleton
@Component(dependencies = {InjectSubComponent.class}
        , modules = {InjectModule.class, ContextModule.class})
public interface InjectComponent {
    @Named("name1")
    IInjectClass getInjectClass();

    void inject(TestJobImpl impl);

    IAbsClass getAbsInject();
}
