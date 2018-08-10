package com.example.nealkyliu.toolsdemo.utils;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author: Created By nealkyliu
 * @date: 2018/8/6
 **/
public class DebugLogger {
    private static final String TAG = "MY_UNIQUE_LOG_TAG";

    private static final int LOG_ID_MAIN = 0;

    public static final int LEVEL_VERBOSE = Log.VERBOSE;
    public static final int LEVEL_DEBUG   = Log.DEBUG;
    public static final int LEVEL_INFO    = Log.INFO;
    public static final int LEVEL_WARNING = Log.WARN;
    public static final int LEVEL_ERROR   = Log.ERROR;

    @IntDef({LEVEL_VERBOSE, LEVEL_DEBUG, LEVEL_INFO, LEVEL_WARNING, LEVEL_ERROR})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface LoggerLevel {}

    private static boolean sEnable = true;

    public static void setEnable(boolean enable) {
        sEnable = enable;
    }

    public static boolean isEnable() {
        return sEnable;
    }

    /* verbose print trace*/
    public static void vp(Object object) {
        v(object, new Exception());
    }

    public static void v(Object object) {
        log(object, LEVEL_VERBOSE);
    }

    public static void v(Object object, Throwable e) {
        log(object, e, LEVEL_VERBOSE);
    }

    public static void v(Object object, Object... values) {
        log(object, LEVEL_VERBOSE, values);
    }

    public static void v(Object object, Throwable e, Object... values) {
        log(object, e, LEVEL_VERBOSE, values);
    }


    public static void dp(Object object) {
        d(object, new Exception());
    }

    public static void d(Object object) {
        log(object, LEVEL_DEBUG);
    }

    public static void d(Object object, Throwable e) {
        log(object, e, LEVEL_DEBUG);
    }

    public static void d(Object object, Object... values) {
        log(object, LEVEL_DEBUG, values);
    }

    public static void d(Object object, Throwable e, Object... values) {
        log(object, e, LEVEL_DEBUG, values);
    }



    public static void ip(Object object) {
        i(object, new Exception());
    }

    public static void i(Object object) {
        log(object, LEVEL_INFO);
    }

    public static void i(Object object, Throwable e) {
        log(object, e, LEVEL_INFO);
    }

    public static void i(Object object, Object... values) {
        log(object, LEVEL_INFO, values);
    }

    public static void i(Object object, Throwable e, Object... values) {
        log(object, e, LEVEL_INFO, values);
    }


    public static void wp(Object object) {
        w(object, new Exception());
    }

    public static void w(Object object) {
        log(object, LEVEL_WARNING);
    }

    public static void w(Object object, Throwable e) {
        log(object, e, LEVEL_WARNING);
    }

    public static void w(Object object, Object... values) {
        log(object, LEVEL_WARNING, values);
    }

    public static void w(Object object, Throwable e, Object... values) {
        log(object, e, LEVEL_WARNING, values);
    }



    public static void ep(Object object) {
        e(object, new Exception());
    }

    public static void e(Object object) {
        log(object, LEVEL_ERROR);
    }

    public static void e(Object object, Throwable e) {
        log(object, e, LEVEL_ERROR);
    }

    public static void e(Object object, Object... values) {
        log(object, LEVEL_ERROR, values);
    }

    public static void e(Object object, Throwable e, Object... values) {
        log(object, e, LEVEL_ERROR, values);
    }




    public static void log(Object object, @LoggerLevel int level) {
        if (isEnable()) {
            Log.println(level, TAG, getMessageInfoFromObject(object));
        }
    }

    public static void log(Object object, Throwable e, @LoggerLevel int level) {
        if (isEnable()) {
            Log.println(level, TAG, getMessageInfoFromObject(object) + '\n' + Log.getStackTraceString(e));
        }
    }

    public static void log(Object object, @LoggerLevel int level, Object... values) {
        if (isEnable()) {
            log(getMessageInfoFromObjects(object, values), level);
        }
    }

    public static void log(Object object, Throwable e, @LoggerLevel int level, Object... values) {
        if (isEnable()) {
            log(getMessageInfoFromObjects(object, values), e, level);
        }
    }



    @NonNull
    private static String getMessageInfoFromObject(@Nullable Object object) {
        String message = "";
        if (null != object) {
            message = (object instanceof String) ? ((String)object) : object.getClass().getSimpleName();
        }
        return message;
    }

    @NonNull
    private static String getMessageInfoFromObjects(Object object, @Nullable Object... values) {
        StringBuilder builder = new StringBuilder(getMessageInfoFromObject(object)).append(": ");
        for (Object value : values) {
            builder.append(null == value ? "null" : value);
        }
        return builder.toString();
    }

}
