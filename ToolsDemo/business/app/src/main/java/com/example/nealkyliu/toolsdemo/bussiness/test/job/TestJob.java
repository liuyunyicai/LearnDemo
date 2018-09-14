package com.example.nealkyliu.toolsdemo.bussiness.test.job;

import android.support.annotation.NonNull;

import com.example.nealkyliu.toolsdemo.utils.DebugLogger;
import com.example.nealkyliu.toolsdemo.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/6
 **/
public class TestJob implements IJob {
    @Override
    public void run() {
        new TestJobImpl().testMain();

        Map<NumbleWrapper, String> map = new TreeMap<>();
        map.put(new NumbleWrapper("Name1", false), "Name1");
        map.put(new NumbleWrapper("Name2", false), "Name2");
        map.put(new NumbleWrapper("Name3", false), "Name3");
        map.put(new NumbleWrapper("Name4", true), "Name4");
        map.put(new NumbleWrapper("Name5", false), "Name5");

        DebugLogger.d(map.toString());
    }



    static class NumbleWrapper implements Comparable<NumbleWrapper>{
        String mName;
        boolean isMain;

        public NumbleWrapper(String name, boolean main) {
            mName = name;
            isMain = main;
        }

        @Override
        public int compareTo(@NonNull NumbleWrapper o) {
            if (isMain) {
                return -1;
            }
            return 1;
        }

        @Override
        public String toString() {
            return "[NumbleWrapper mName = " + mName + "; isMain = " + isMain + "]";
        }
    }
}
