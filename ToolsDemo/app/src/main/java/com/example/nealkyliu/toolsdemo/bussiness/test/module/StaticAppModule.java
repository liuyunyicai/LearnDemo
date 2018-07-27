package com.example.nealkyliu.toolsdemo.bussiness.test.module;

import com.example.nealkyliu.toolsdemo.bussiness.test.classed.InjectSetClass;

import java.util.Set;

import dagger.Module;
import dagger.multibindings.Multibinds;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/11
 **/
@Module
public abstract class StaticAppModule {
    @Multibinds
    abstract Set<InjectSetClass> aSet();
}
