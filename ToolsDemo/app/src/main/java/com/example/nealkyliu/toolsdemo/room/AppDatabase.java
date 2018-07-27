package com.example.nealkyliu.toolsdemo.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.helloworld.AppSetting;
import com.example.nealkyliu.toolsdemo.bussiness.config.save.db.AppSettingDao;
import com.example.nealkyliu.toolsdemo.bussiness.config.save.db.Converters;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/16
 **/
@Database(entities = {User.class, AppSetting.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract AppSettingDao appSettingDao();
}
