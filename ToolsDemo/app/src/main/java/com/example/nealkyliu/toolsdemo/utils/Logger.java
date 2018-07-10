package com.example.nealkyliu.toolsdemo.utils;

import android.util.Log;

/**
 * Created by nealkyliu on 2018/6/20.
 */

public class Logger {

    public static final String TAG = "ToolsDemoLog";

    public static void d(String info) {
        d("", info);
    }

    public static void d(Object object, String info) {
        d(null == object ? "" : object.getClass().getSimpleName(), info);
    }

    public static void d(String sectag, String info) {
        Log.d(TAG, sectag + ":" + info);
    }
}
