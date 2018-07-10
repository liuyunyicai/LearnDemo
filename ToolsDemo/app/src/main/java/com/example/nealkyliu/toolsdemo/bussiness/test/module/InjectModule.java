package com.example.nealkyliu.toolsdemo.bussiness.test.module;

import com.example.nealkyliu.toolsdemo.bussiness.test.classed.IAbsClass;
import com.example.nealkyliu.toolsdemo.bussiness.test.classed.IInjectClass;
import com.example.nealkyliu.toolsdemo.bussiness.test.classed.InjectClass;
import com.example.nealkyliu.toolsdemo.bussiness.test.classed.InjectClass1;
import com.example.nealkyliu.toolsdemo.bussiness.test.classed.InjectClass2;
import com.example.nealkyliu.toolsdemo.bussiness.test.classed.InjectClass3;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/6
 **/
@Module(includes = {InjectModule2.class})
public class InjectModule {

    @Provides
    public InjectClass1 getClass1() {
        return new InjectClass1();
    }

    @Provides
    @Singleton
    @Named("name1")
    public IInjectClass getTestClassInstance() {
        return new InjectClass();
    }

    @Provides
    @Named("name2")
    public IInjectClass getTestClassInstance1() {
        return new InjectClass1();
    }

    @Provides
    public InjectClass2 getInjectClass2(InjectClass3 class3) {
        return new InjectClass2();
    }
}
