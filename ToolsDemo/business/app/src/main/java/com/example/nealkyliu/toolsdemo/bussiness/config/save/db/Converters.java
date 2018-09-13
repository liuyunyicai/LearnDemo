package com.example.nealkyliu.toolsdemo.bussiness.config.save.db;

import android.arch.persistence.room.TypeConverter;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/23
 **/
public class Converters {
    @TypeConverter
    public static String fromString(String value) {
        return value;
    }
}
