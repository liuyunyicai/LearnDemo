package com.example.nealkyliu.toolsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.example.nealkyliu.toolsdemo.bussiness.test.job.TestExecutor;
import com.example.nealkyliu.toolsdemo.lifecycle.LifeCycleActivity;
import com.example.nealkyliu.toolsdemo.livedata.activity.AbsDemoActivity;
import com.example.nealkyliu.toolsdemo.livedata.activity.TestNameActivity;
import com.example.nealkyliu.toolsdemo.livedata.fragment.NameFragment1;
import com.example.nealkyliu.toolsdemo.livedata.fragment.NameFragment2;
import com.example.nealkyliu.toolsdemo.livedata.fragment.NameFragment3;
import com.example.nealkyliu.toolsdemo.utils.Logger;

import dagger.android.AndroidInjection;

public class MainActivity extends AbsDemoActivity {
    private Button mText;
    private Button mGoLifeCycle;
    boolean mSwitched = false;

    private NameFragment1 mNameFragment1 = new NameFragment1();
    private NameFragment2 mNameFragment2 = new NameFragment2();
    private NameFragment3 mNameFragment3 = new NameFragment3();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        // Other code to setup the activity...
        mText = findViewById(R.id.mText);
        mGoLifeCycle = findViewById(R.id.mGoLifeCycle);

        addOnClickListener(mText);
        addOnClickListener(mGoLifeCycle);

        resumeFragment(R.id.fragment_container2, mNameFragment3);

        TestExecutor.executeTest();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Class getSwitchActivityClass() {
        return TestNameActivity.class;
    }

    private void resumeFragment(int containerId, @NonNull Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(containerId, fragment).commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.mText:
                Fragment toSwitchFragment = mSwitched ? mNameFragment2 : mNameFragment1;
                mSwitched = !mSwitched;
                resumeFragment(R.id.fragment_container, toSwitchFragment);
                break;
            case R.id.mGoLifeCycle:
                startActivity(new Intent(this, LifeCycleActivity.class));
                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
