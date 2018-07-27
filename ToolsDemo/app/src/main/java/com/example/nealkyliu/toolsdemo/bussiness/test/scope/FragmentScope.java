package com.example.nealkyliu.toolsdemo.bussiness.test.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/11
 **/
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface FragmentScope {
}
