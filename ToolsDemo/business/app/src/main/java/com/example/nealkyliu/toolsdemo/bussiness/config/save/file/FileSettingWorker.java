package com.example.nealkyliu.toolsdemo.bussiness.config.save.file;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.nealkyliu.toolsdemo.bussiness.config.save.base.AbsSettingWorker;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/22
 **/
public class FileSettingWorker extends AbsSettingWorker {


    public FileSettingWorker(@NonNull Context context) {
        super(context);
    }

    @Override
    protected String getTag() {
        return "File";
    }

    @Override
    protected void doSaveAll() {
        for (int i = 0; i < mKeyCount; i++) {

        }
    }

    @Override
    protected void doLoadAll() {

    }
}
