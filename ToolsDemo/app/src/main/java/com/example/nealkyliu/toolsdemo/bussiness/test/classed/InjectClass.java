package com.example.nealkyliu.toolsdemo.bussiness.test.classed;

import android.support.annotation.NonNull;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/6
 **/
public class InjectClass extends AbsInjectClass{
    @NonNull
    @Override
    protected String getTag() {
        return "InjectClass";
    }
}
