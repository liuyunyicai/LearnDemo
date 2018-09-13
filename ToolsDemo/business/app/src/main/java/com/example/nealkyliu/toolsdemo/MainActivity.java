package com.example.nealkyliu.toolsdemo;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.VisibleModle1;
import com.example.nealkyliu.toolsdemo.bus.LiveDataBus;
import com.example.nealkyliu.toolsdemo.bus.eventbus.MessageEvent;
import com.example.nealkyliu.toolsdemo.bussiness.config.AppConfigCenter;
import com.example.nealkyliu.toolsdemo.bussiness.test.job.TestExecutor;
import com.example.nealkyliu.toolsdemo.lifecycle.FragmentLifeCycleImpl;
import com.example.nealkyliu.toolsdemo.lifecycle.LifeCycleActivity;
import com.example.nealkyliu.toolsdemo.livedata.activity.AbsDemoActivity;
import com.example.nealkyliu.toolsdemo.livedata.activity.TestNameActivity;
import com.example.nealkyliu.toolsdemo.livedata.fragment.NameFragment1;
import com.example.nealkyliu.toolsdemo.livedata.fragment.NameFragment2;
import com.example.nealkyliu.toolsdemo.livedata.fragment.NameFragment3;
import com.example.nealkyliu.toolsdemo.recyclerview.settings.WhiteListSelectActivity;
import com.example.nealkyliu.toolsdemo.utils.DebugLogger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AbsDemoActivity {
    private Button mText;
    private Button mGoLifeCycle;
    private Button mGetAppSettingValueBt;
    private Button mSaveAppSettingBt;
    boolean mSwitched = false;

    TextView mTv;

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
        mGetAppSettingValueBt = findViewById(R.id.mGetAppSetting);
        mSaveAppSettingBt =findViewById(R.id.mSaveAppSetting);

        addOnClickListener(mText);
        addOnClickListener(mGoLifeCycle);
        addOnClickListener(mGetAppSettingValueBt);
        addOnClickListener(mSaveAppSettingBt);

        resumeFragment(R.id.fragment_container2, mNameFragment3);

        AppConfigCenter.init(this);

        TestExecutor.executeTest();

        getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentLifeCycleImpl(), true);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


//        LiveDataBus.get().getChannel("key_test", Boolean.class)
//                .observe(this, new Observer<Boolean>() {
//                    @Override
//                    public void onChanged(@Nullable Boolean aBoolean) {
//                        DebugLogger.d("LiveDataBus", aBoolean);
//                    }
//                });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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

                startActivity(new Intent(this, WhiteListSelectActivity.class));
                break;
            case R.id.mGoLifeCycle:
                startActivity(new Intent(this, LifeCycleActivity.class));
                break;
            case R.id.mGetAppSetting:
                AppConfigCenter.getInstance().loadAll();
                break;
            case R.id.mSaveAppSetting:
                AppConfigCenter.getInstance().saveAll();
                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
