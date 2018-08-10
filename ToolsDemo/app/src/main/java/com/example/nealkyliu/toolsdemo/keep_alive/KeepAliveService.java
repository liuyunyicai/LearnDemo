package com.example.nealkyliu.toolsdemo.keep_alive;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.nealkyliu.toolsdemo.utils.LogUtils;

/**
 * @author: Created By nealkyliu
 * @date: 2018/8/6
 **/
public class KeepAliveService extends Service {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        LogUtils.d("KeepAliveService", "attachBaseContext start");
        enableCompontent(this, false);
        LogUtils.d("KeepAliveService", "attachBaseContext end");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtils.d("KeepAliveService", "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d("KeepAliveService", "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void enableCompontent(Context context, boolean enable) {
        PackageManager pm  = context.getApplicationContext().getPackageManager();
        ComponentName componentName = new ComponentName(context.getPackageName(),
                "com.example.nealkyliu.toolsdemo.keep_alive.KeepAliveService");
        pm.setComponentEnabledSetting(componentName,
                enable ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }
}
