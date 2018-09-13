package com.example.nealkyliu.toolsdemo.bussiness.test.job;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/6
 **/
public class TestJob implements IJob {
    @Override
    public void run() {
        new TestJobImpl().testMain();
    }
}
