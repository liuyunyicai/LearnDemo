package com.example.nealkyliu.toolsdemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.nealkyliu.toolsdemo.R;
import com.example.nealkyliu.toolsdemo.livedata.activity.AbsActivity;
import com.example.nealkyliu.toolsdemo.utils.LogUtils;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/23
 **/
public class MainViewActivity extends AbsActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtils.d("MainViewActivity", "onCreate");
    }

    @Override
    protected void initView() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_view;
    }

    @Override
    protected void initData() {

    }
}
