package com.example.nealkyliu.toolsdemo.bussiness.config.save.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.nealkyliu.toolsdemo.bussiness.config.save.base.AbsSettingWorker;

import java.util.Map;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/22
 **/
public class SpSettingWorker extends AbsSettingWorker {

    private static final String SP_NAME = "sp_name";
    protected SharedPreferences mSp;

    public SpSettingWorker(@NonNull Context context) {
        super(context);
        mSp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    @Override
    protected String getTag() {
        return "SharePreference";
    }

    @Override
    protected void doSaveAll() {
        SharedPreferences.Editor editor = mSp.edit();
        for (int i = 0; i < mKeyCount; i++) {
            editor.putString("key_" + i, "value_" + i);
        }
        editor.apply();
    }

    @Override
    protected void doLoadAll() {
        mDatas = mSp.getAll();
    }
}
