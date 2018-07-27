package com.example.nealkyliu.toolsdemo.bussiness.config.save.base;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.nealkyliu.toolsdemo.bussiness.config.save.ISettingWorker;
import com.example.nealkyliu.toolsdemo.utils.Logger;

import java.util.Map;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/22
 **/
public abstract class AbsSettingWorker implements ISettingWorker {
    protected Context mAppContext;
    protected int mKeyCount = 999;

    protected Map<String, ?> mDatas;

    public AbsSettingWorker(@NonNull Context context) {
        mAppContext = context.getApplicationContext();
    }

    @Override
    public void saveAll() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                doSaveAll();
                logCostTime("SaveData", startTime);
            }
        }).start();
    }

    @Override
    public void loadAll() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                doLoadAll();
                logCostTime("LoadData", startTime);
            }
        }).start();

    }

    private void logCostTime(String secondTag, long startTime) {
        long endTime = System.currentTimeMillis();
        Logger.d(getTag() + secondTag, "Cost Time == " + (endTime - startTime));
    }

    protected abstract String getTag();

    protected abstract void doSaveAll();

    protected abstract void doLoadAll();
}
