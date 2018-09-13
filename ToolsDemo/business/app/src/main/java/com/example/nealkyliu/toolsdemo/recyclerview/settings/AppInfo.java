package com.example.nealkyliu.toolsdemo.recyclerview.settings;

import android.support.annotation.NonNull;

/**
 * @author: Created By nealkyliu
 * @date: 2018/8/1
 **/
public class AppInfo implements Comparable<AppInfo>{
    private static int BASE_INDEX = 1000;

    public int mId;
    public String appName;
    public String packageName;
    public boolean needMonitor;

    public AppInfo() {
        mId = BASE_INDEX++;
    }

    public int getId() {
        return mId;
    }


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isNeedMonitor() {
        return needMonitor;
    }

    public void setNeedMonitor(boolean needMonitor) {
        this.needMonitor = needMonitor;
    }




    @Override
    public int compareTo(@NonNull AppInfo o) {
        if (needMonitor != o.needMonitor) {
            return needMonitor ? -1 : 1;
        }
        return 0;
    }
}
