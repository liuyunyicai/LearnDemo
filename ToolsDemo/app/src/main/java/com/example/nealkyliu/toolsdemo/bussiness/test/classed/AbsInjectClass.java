package com.example.nealkyliu.toolsdemo.bussiness.test.classed;

import android.support.annotation.NonNull;

import com.example.nealkyliu.toolsdemo.kotlin.ClassB;
import com.example.nealkyliu.toolsdemo.utils.Logger;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/8
 **/
public abstract class AbsInjectClass implements IInjectClass {
    @Override
    public void test() {
        new ClassB().getUnit();
        Logger.d(this, getTag() + " test");
    }

    @NonNull
    protected abstract String getTag();
}
