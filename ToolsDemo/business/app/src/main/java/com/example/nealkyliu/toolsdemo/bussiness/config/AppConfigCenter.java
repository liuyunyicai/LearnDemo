package com.example.nealkyliu.toolsdemo.bussiness.config;

import android.content.Context;
import android.support.annotation.NonNull;


import com.example.nealkyliu.toolsdemo.bussiness.config.save.ISettingWorker;
import com.example.nealkyliu.toolsdemo.bussiness.config.save.base.AbsSettingWorker;
import com.example.nealkyliu.toolsdemo.bussiness.config.save.db.Converters;
import com.example.nealkyliu.toolsdemo.bussiness.config.save.db.DbSettingWorker;
import com.example.nealkyliu.toolsdemo.bussiness.config.save.file.FileSettingWorker;
import com.example.nealkyliu.toolsdemo.bussiness.config.save.sp.SpSettingWorker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/18
 **/
public class AppConfigCenter implements ISettingWorker{
    private List<AbsSettingWorker> mWorkers = new ArrayList<>();

    private Map<String, Object> values = new HashMap<>();

    private static Context mAppContext;

    private static volatile AppConfigCenter mInstance = null;

    private AppConfigCenter() {
        mWorkers.add(new SpSettingWorker(mAppContext));
        mWorkers.add(new FileSettingWorker(mAppContext));
        mWorkers.add(new DbSettingWorker(mAppContext));
    }

    public static AppConfigCenter getInstance() {
        if (null == mInstance) {
            synchronized (AppConfigCenter.class) {
                if (null == mInstance) {
                    mInstance = new AppConfigCenter();
                }
            }
        }
        return mInstance;
    }

    public <T> T getValue(@NonNull String key, T defaultValue) {
        T result = defaultValue;
        Object value = values.get(key);
        if (null != value) {
            try {
                result = (T) value;
            } catch (Throwable e) {

            }
        }
        return result;
    }

    public static void init(@NonNull Context context) {
        mAppContext = context.getApplicationContext();
    }


    @Override
    public void saveAll() {
        for (AbsSettingWorker worker : mWorkers) {
            worker.saveAll();
        }
    }

    @Override
    public void loadAll() {
        for (AbsSettingWorker worker : mWorkers) {
            worker.loadAll();
        }
    }
}
