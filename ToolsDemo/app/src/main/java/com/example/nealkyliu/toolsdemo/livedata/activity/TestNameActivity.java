package com.example.nealkyliu.toolsdemo.livedata.activity;

import com.example.nealkyliu.toolsdemo.MainActivity;
import com.example.nealkyliu.toolsdemo.R;

/**
 * Created by nealkyliu on 2018/6/25.
 */

public class TestNameActivity extends AbsDemoActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_name;
    }

    @Override
    protected Class getSwitchActivityClass() {
        return MainActivity.class;
    }
}
