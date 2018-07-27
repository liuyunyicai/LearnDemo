package com.example.nealkyliu.toolsdemo.bussiness.config.save.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.helloworld.AppSetting;
import com.example.setting.SettingValue;

import java.util.List;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/22
 **/
@SettingValue
@Dao
public interface AppSettingDao {

    @Query("SELECT * FROM appsetting")
    List<AppSetting> getAllData();


    @Insert
    void insert(AppSetting appSetting);

}
