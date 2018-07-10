package com.example.nealkyliu.toolsdemo.livedata.demo;

import android.arch.lifecycle.LiveData;

/**
 * Created by nealkyliu on 2018/6/23.
 */

public class NameLiveData<T> extends LiveData<T> {




    @Override
    public void setValue(T value) {
        super.setValue(value);
    }
}
