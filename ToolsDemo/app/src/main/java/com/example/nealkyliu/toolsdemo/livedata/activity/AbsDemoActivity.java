package com.example.nealkyliu.toolsdemo.livedata.activity;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nealkyliu.toolsdemo.R;
import com.example.nealkyliu.toolsdemo.livedata.ExtendedViewModelProviders;
import com.example.nealkyliu.toolsdemo.livedata.demo.ExtendedNameBean;
import com.example.nealkyliu.toolsdemo.livedata.demo.NameViewModel;
import com.example.nealkyliu.toolsdemo.utils.Logger;

/**
 * Created by nealkyliu on 2018/6/25.
 */

public abstract class AbsDemoActivity extends AppCompatActivity implements View.OnClickListener{
    protected NameViewModel mModel;
    private TextView mNameText;
    private Button mSwitchText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initData();
    }

    private void initView() {
        mNameText = findViewById(R.id.mNameText);
        mSwitchText = findViewById(R.id.mSwitchText);

        mSwitchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AbsDemoActivity.this, getSwitchActivityClass());
                AbsDemoActivity.this.startActivity(intent);
            }
        });
    }

    protected abstract int getLayoutId();

    protected abstract Class getSwitchActivityClass();

    private void initData() {
        // Get the ViewModel.
        mModel = ExtendedViewModelProviders.of(getApplication()).get(NameViewModel.class);

        // Create the observer which updates the UI.
        final Observer<ExtendedNameBean> nameObserver = new Observer<ExtendedNameBean>() {
            @Override
            public void onChanged(@Nullable final ExtendedNameBean newName) {
                // Update the UI, in this case, a TextView.
                Logger.d(this, "onChanged " + newName);
                if (null != newName) {
                    mNameText.setText(getActivityText() + newName.toString());
                }
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mModel.getExtendedName().observe(this, nameObserver);

        mNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExtendedNameBean value = mModel.getExtendedName().getValue();
                if (value == null) {
                    value = new ExtendedNameBean();
                }
                value.count++;

                mModel.getExtendedName().setValue(value);
            }
        });
    }

    protected String getActivityText() {
        return getClass().getSimpleName();
    }

    @Override
    public void onClick(View v) {

    }

    protected void addOnClickListener(@NonNull View view) {
        view.setOnClickListener(this);
    }
}
