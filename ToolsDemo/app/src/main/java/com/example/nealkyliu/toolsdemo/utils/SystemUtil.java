package com.example.nealkyliu.toolsdemo.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.example.nealkyliu.toolsdemo.recyclerview.settings.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 2017/9/15.
 */

public class SystemUtil {

    private static String sDeviceId;
    private static String sAndroidId;
    private static String sImei;

    private static String getAndroidId(Context context) {
        try {
            if (TextUtils.isEmpty(sAndroidId)) {
                sAndroidId = android.provider.Settings.Secure
                    .getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sAndroidId;
    }

    public static List<AppInfo> scanLocalInstallAppList(@NonNull Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<AppInfo> myAppInfos = new ArrayList<>();
        try {
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i);
                //过滤掉系统app
                if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
                    continue;
                }
                AppInfo myAppInfo = new AppInfo();
                myAppInfo.setAppName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
                myAppInfo.setPackageName(packageInfo.packageName);
                myAppInfos.add(myAppInfo);
            }
        }catch (Exception e){
        }
        return myAppInfos;
    }

}
