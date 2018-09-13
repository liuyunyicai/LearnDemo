package com.example.nealkyliu.toolsdemo.rxjava;

import com.example.nealkyliu.toolsdemo.bussiness.test.job.IJob;
import com.example.nealkyliu.toolsdemo.utils.DebugLogger;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * @author: Created By nealkyliu
 * @date: 2018/8/23
 **/
public class TestJob2 implements IJob {
    private List<Observable<String>> mTransformations = new LinkedList<>();

    private List<String> mList = new LinkedList<>();

    public void add(String str) {
        mTransformations.add(Observable.fromCallable(() -> str));
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            add("mTransformations " + i);
        }

        for (int i = 0; i < 10; i++) {
            mList.add("djsdfksdf" + i);
        }

        Iterator<String> iterator = mList.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }

    }
}
