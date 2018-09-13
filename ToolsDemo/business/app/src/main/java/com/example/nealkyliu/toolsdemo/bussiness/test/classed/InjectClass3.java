package com.example.nealkyliu.toolsdemo.bussiness.test.classed;

import android.support.annotation.NonNull;

import javax.inject.Inject;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/8
 **/
public class InjectClass3 extends AbsInjectClass {

    @Inject
    public InjectClass3() {

    }

    @NonNull
    @Override
    protected String getTag() {
        return "InjectClass3";
    }
}
