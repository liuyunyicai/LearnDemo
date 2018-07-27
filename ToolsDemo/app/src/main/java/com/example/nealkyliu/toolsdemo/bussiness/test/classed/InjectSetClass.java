package com.example.nealkyliu.toolsdemo.bussiness.test.classed;

import android.support.annotation.NonNull;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/11
 **/
public class InjectSetClass extends AbsInjectClass{
    private String mTag;

    public InjectSetClass(String tag) {
        mTag = tag;
    }

    @NonNull
    @Override
    protected String getTag() {
        return "InjectSetClass:" + mTag;
    }
}
