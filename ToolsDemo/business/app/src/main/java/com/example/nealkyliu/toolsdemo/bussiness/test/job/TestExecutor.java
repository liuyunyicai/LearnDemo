package com.example.nealkyliu.toolsdemo.bussiness.test.job;

import com.example.nealkyliu.toolsdemo.rxjava.TestJob2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/6
 **/
public class TestExecutor {
    private List<IJob> mJobs = new ArrayList<>();

    public TestExecutor() {
        mJobs.add(new TestJob());
        mJobs.add(new TestJob2());
    }

    public void addJob(IJob job) {
        mJobs.add(job);
    }

    public void removeJob(IJob job) {
        mJobs.remove(job);
    }

    public void execute() {
        for (IJob job : mJobs) {
            job.run();
        }
    }

    public static void executeTest() {
        new TestExecutor().execute();
    }
}
