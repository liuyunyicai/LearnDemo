package com.example.nealkyliu.toolsdemo.bussiness.config.save.db;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.helloworld.AppSetting;
import com.example.nealkyliu.toolsdemo.LiveDataApplication;
import com.example.nealkyliu.toolsdemo.bussiness.config.save.base.AbsSettingWorker;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/22
 **/
public class DbSettingWorker extends AbsSettingWorker {
    private AppSettingDao dao;

    public DbSettingWorker(@NonNull Context context) {
        super(context);
        dao = LiveDataApplication.getInst().getAppDb().appSettingDao();
    }

    @Override
    protected String getTag() {
        return "Database";
    }

    @Override
    protected void doSaveAll() {
        dao.insert(new AppSetting());
    }

    @Override
    protected void doLoadAll() {
        dao.getAllData();
    }
}
