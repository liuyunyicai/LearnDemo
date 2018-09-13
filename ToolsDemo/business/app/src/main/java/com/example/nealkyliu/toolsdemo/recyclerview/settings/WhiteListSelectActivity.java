package com.example.nealkyliu.toolsdemo.recyclerview.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.nealkyliu.toolsdemo.R;


/**
 * @author: Created By nealkyliu
 * @date: 2018/8/1
 **/
public class WhiteListSelectActivity extends BaseActivity {
    private RecyclerView mSelectRyview;
    private WhiteListSelectAdapter mSelectAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_white_list_select;
    }

    @Override
    protected void initView() {
        mSelectRyview = $(R.id.whitelist_select_ryview);
        mSelectRyview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mSelectAdapter = new WhiteListSelectAdapter(this, WhiteListManager.getAppList());
        mSelectRyview.setAdapter(mSelectAdapter);
    }
}
