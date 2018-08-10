package com.example.nealkyliu.toolsdemo.recyclerview.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author: Created By nealkyliu
 * @date: 2018/8/1
 **/
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();


    protected <T> T $(int res) {
        return (T) findViewById(res);
    }
}
