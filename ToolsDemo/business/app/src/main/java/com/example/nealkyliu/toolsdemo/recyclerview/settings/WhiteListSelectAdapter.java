package com.example.nealkyliu.toolsdemo.recyclerview.settings;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.nealkyliu.toolsdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Created By nealkyliu
 * @date: 2018/8/1
 **/
public class WhiteListSelectAdapter extends RecyclerView.Adapter<WhiteListViewHolder> {
    private Context mContext;
    private List<AppInfo> mDatas;

    public WhiteListSelectAdapter(@NonNull Context context, @NonNull List<AppInfo> datas) {
        mContext = context;
        mDatas = datas;
        setHasStableIds(true);
    }

    private int getLayoutId() {
        return R.layout.item_white_list_adapter;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public WhiteListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WhiteListViewHolder(this, getInflate().inflate(getLayoutId(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WhiteListViewHolder holder, int position) {
        holder.bindData(getItemData(position));
    }

    public void doNotifyChange() {
        List<AppInfo> newDatas = new ArrayList<>();
        newDatas.addAll(mDatas);
        newDatas.remove(10);


        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtils.MyCallBack<>(mDatas, newDatas), true);
        //利用DiffUtil.DiffResult对象的dispatchUpdatesTo（）方法，传入RecyclerView的Adapter
        diffResult.dispatchUpdatesTo(this);
        mDatas = newDatas;
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return getItemData(position).mId;
    }

    public AppInfo getItemData(int position) {
        return mDatas.get(position);
    }

    private LayoutInflater getInflate() {
        return LayoutInflater.from(mContext);
    }
}
