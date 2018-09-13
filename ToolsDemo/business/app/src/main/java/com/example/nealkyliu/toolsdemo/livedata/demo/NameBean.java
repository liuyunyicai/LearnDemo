package com.example.nealkyliu.toolsdemo.livedata.demo;

/**
 * Created by nealkyliu on 2018/6/23.
 */

public class NameBean {
    public String mBasicName = "DefaultNameBean";
    public int count;

    @Override
    public String toString() {
        return getBasicName() + count;
    }

    protected String getBasicName() {
        return "DefaultNameBean";
    }
}
