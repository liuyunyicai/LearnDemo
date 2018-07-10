package com.example.nealkyliu.toolsdemo.worker;

import android.support.annotation.NonNull;

import com.example.nealkyliu.toolsdemo.utils.Logger;

import androidx.work.Worker;

/**
 * Created by nealkyliu on 2018/6/20.
 */

public class CleanUpWorker extends Worker{
    private static final String TAG = "CleanUpWorker";

    @NonNull
    @Override
    public Result doWork() {
        Logger.d(TAG, "doWork");
        return Result.SUCCESS;
    }
}
