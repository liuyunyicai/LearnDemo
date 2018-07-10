package com.example.nealkyliu.toolsdemo.worker;

import android.arch.lifecycle.Observer;
import android.database.Observable;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkStatus;
import androidx.work.Worker;

/**
 * Created by nealkyliu on 2018/6/20.
 */

public class WorkerManagerHelper {

    public void enqueueTask(Class<? extends Worker> clazz) {
        if (null == clazz) {
            return;
        }
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(clazz)
                .setConstraints(new Constraints())
                .build();


        WorkManager.getInstance().enqueue(request);
        WorkManager.getInstance().beginWith(request).then(request).enqueue();

        WorkManager.getInstance().getStatusById(request.getId()).observe(null, new Observer<WorkStatus>() {
            @Override
            public void onChanged(@Nullable WorkStatus workStatus) {

            }
        });
    }
}
