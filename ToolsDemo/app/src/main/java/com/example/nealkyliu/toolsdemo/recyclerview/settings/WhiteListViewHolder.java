package com.example.nealkyliu.toolsdemo.recyclerview.settings;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.nealkyliu.toolsdemo.R;


/**
 * @author: Created By nealkyliu
 * @date: 2018/8/1
 **/
public class WhiteListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mAppNameTv;
    private TextView mPackagenameTv;
    private View mItemRoot;

    private WhiteListSelectAdapter mAdapter;
    private AppInfo mData;


    public WhiteListViewHolder(WhiteListSelectAdapter adapter, View itemView) {
        super(itemView);
        mAdapter = adapter;
        mAppNameTv     = $(itemView, R.id.app_name_tv);
        mPackagenameTv = $(itemView, R.id.package_name_tv);
        mItemRoot      = $(itemView, R.id.item_root);
    }

    public void bindData(AppInfo info) {
        mData = info;
        mAppNameTv.setText(info.getAppName());
        mPackagenameTv.setText(info.getPackageName());
        mItemRoot.setBackgroundResource(getBgRes(info.isNeedMonitor()));
        mItemRoot.setOnClickListener(this);
    }

    private int getBgRes(boolean selected) {
        return selected ? R.color.c6 : R.color.white;
    }

    private <T> T $(@NonNull View view, int res) {
        return (T) view.findViewById(res);
    }

    @Override
    public void onClick(View v) {
        WhiteListManager.markAppStateChanged(mData);
        mAdapter.doNotifyChange();
    }
}
