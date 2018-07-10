package com.example.nealkyliu.toolsdemo.bussiness.test.compontent;

import com.example.nealkyliu.toolsdemo.bussiness.test.classed.InjectClass3;
import com.example.nealkyliu.toolsdemo.bussiness.test.module.ContextModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Subcomponent;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/8
 **/
@Component(modules = {ContextModule.class})
public interface InjectSubComponent {
    InjectClass3 getInjectClass3();
}
