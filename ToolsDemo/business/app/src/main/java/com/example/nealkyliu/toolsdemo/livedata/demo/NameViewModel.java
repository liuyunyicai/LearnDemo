package com.example.nealkyliu.toolsdemo.livedata.demo;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by nealkyliu on 2018/6/22.
 */

public class NameViewModel extends ViewModel {
    // Create a LiveData with a String
    private NameLiveData<NameBean> mCurrentName;

    private NameLiveData<ExtendedNameBean> mExtendedName;

    public NameLiveData<ExtendedNameBean> getExtendedName() {
        if (null == mExtendedName) {
            mExtendedName = new NameLiveData();
        }
        return mExtendedName;
    }

    public NameLiveData<NameBean> getCurrentName() {
        if (mCurrentName == null) {
            mCurrentName = new NameLiveData<>();
        }
        return mCurrentName;
    }
}
