package com.example.nealkyliu.toolsdemo.recyclerview;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.nealkyliu.toolsdemo.LiveDataApplication;

/**
 * @author: Created By nealkyliu
 * @date: 2018/8/1
 **/
public class DataSpSaver {
    private static final String SP_NAME = "SP_SETTINGS";

    public static SharedPreferences getSharedPreferences() {
        return LiveDataApplication.getInst().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }


    public static SharedPreferences.Editor getEditor() {
        return getSharedPreferences().edit();
    }


    public static <T> T get(@NonNull String key, @NonNull T defaultValue) {
        Object res = getSharedPreferences().getAll().get(key);

        if (defaultValue.getClass().isInstance(res)) {
            return (T) res;
        }
        return defaultValue;
    }

    public static <T> void save(@NonNull String key, @NonNull T value) {
        saveValue(getEditor(), key, value);
    }

    public static <T> void saveValue(@NonNull SharedPreferences.Editor editor, @NonNull String key, @NonNull T value) {
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        }

        editor.apply();
    }

}
