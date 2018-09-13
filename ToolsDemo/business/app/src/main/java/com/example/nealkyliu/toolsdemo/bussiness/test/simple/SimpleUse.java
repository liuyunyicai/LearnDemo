package com.example.nealkyliu.toolsdemo.bussiness.test.simple;

import com.example.nealkyliu.toolsdemo.bussiness.test.classed.IInjectClass;

import javax.inject.Inject;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/8
 **/
public class SimpleUse {
    private IInjectClass mIInjectClass;

    @Inject
    public SimpleUse(IInjectClass injectClass) {
        mIInjectClass = injectClass;
    }

}
