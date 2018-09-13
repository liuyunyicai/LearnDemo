package com.example.nealkyliu.toolsdemo.bussiness.test.module;

import com.example.nealkyliu.toolsdemo.bussiness.test.classed.IAbsClass;
import com.example.nealkyliu.toolsdemo.bussiness.test.classed.IAbsClassImpl1;
import com.example.nealkyliu.toolsdemo.bussiness.test.classed.IInjectClass;
import com.example.nealkyliu.toolsdemo.bussiness.test.classed.InjectClass2;
import com.example.nealkyliu.toolsdemo.bussiness.test.classed.InjectClass3;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/8
 **/
@Module
public abstract class InjectModule2 {
    @Provides
    public static IInjectClass provideInjectClass1() {
        return new InjectClass3();
    }

    @Binds
    public abstract IAbsClass getAbsClassImpl(IAbsClassImpl1 impl);
}
