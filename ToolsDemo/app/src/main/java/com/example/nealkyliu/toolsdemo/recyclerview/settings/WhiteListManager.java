package com.example.nealkyliu.toolsdemo.recyclerview.settings;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.nealkyliu.toolsdemo.LiveDataApplication;
import com.example.nealkyliu.toolsdemo.recyclerview.DataSpSaver;
import com.example.nealkyliu.toolsdemo.utils.SystemUtil;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by patrick on 14/06/2017.
 */

public class WhiteListManager {
    public static final String KEY_SP_WHITE_LIST_APPS = "white_list_apps";

    private static Set<String> mWhiteListSet;

    static {
        mWhiteListSet = getWhiteListFromLocal();

        if (null == mWhiteListSet) {
            mWhiteListSet = new HashSet<>(20);
        }
    }

    /**
     * Get All App List
     **/
    public static List<AppInfo> getAppList() {
        return markAppListState(getAppListFromDevice());
    }

    private static List<AppInfo> getAppListFromDevice() {
        return SystemUtil.scanLocalInstallAppList(LiveDataApplication.getInst());
    }

    private static synchronized List<AppInfo> markAppListState(@NonNull List<AppInfo> appInfos) {
        for (AppInfo data : appInfos) {
            data.needMonitor = needMonitor(data.packageName);

        }
        Collections.sort(appInfos);
        return appInfos;
    }

    public static void markAppStateChanged(@NonNull AppInfo appInfo) {
        appInfo.setNeedMonitor(!appInfo.isNeedMonitor());

        if (!appInfo.isNeedMonitor()) {
            mWhiteListSet.remove(appInfo.getPackageName());
        } else {
            mWhiteListSet.add(appInfo.getPackageName());
        }
        saveWhiteListIntoSp(mWhiteListSet);
    }

    @Nullable
    public static Set<String>  getWhiteListFromLocal() {
        return new Gson().fromJson(DataSpSaver.get(KEY_SP_WHITE_LIST_APPS, ""), new TypeToken<Set<String>>(){}.getType());
    }

    public static void saveWhiteListIntoSp(@NonNull Set<String> appInfos) {
        DataSpSaver.save(KEY_SP_WHITE_LIST_APPS, new Gson().toJson(appInfos));
    }

    public static boolean needMonitor(String packageName) {
        return mWhiteListSet.contains(packageName);
    }

    public static boolean isNotValidNotification(String title, String packageName) {
        if (TextUtils.isEmpty(title)) {
            return true;
        } else if (title.endsWith("正在运行")) {
            return true;
        } else if ("android".equals(packageName)) {
            return true;
        } else if (!needMonitor(packageName)) {
            return true;
        }
        return false;
    }

    public static String getApplicationName(Context context, String packageName) {
        String name = "";
        if (context == null || TextUtils.isEmpty(packageName)) {
            return name;
        }
        PackageManager pm = context.getPackageManager();
        try {
            name = pm.getApplicationLabel(
                pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }
}
