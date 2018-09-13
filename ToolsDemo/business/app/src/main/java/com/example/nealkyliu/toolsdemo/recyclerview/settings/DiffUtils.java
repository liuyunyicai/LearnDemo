package com.example.nealkyliu.toolsdemo.recyclerview.settings;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * @author: Created By nealkyliu
 * @date: 2018/8/7
 **/
public class DiffUtils {
    public static class MyCallBack<T> extends DiffUtil.Callback {

        protected List<T> mOldList;
        protected List<T> mNewList;

        public MyCallBack(@Nullable List<T> oldList, @Nullable List<T> newList) {
            mOldList = oldList;
            mNewList = newList;
        }

        @Override
        public int getOldListSize() {
            return getListSize(mOldList);
        }

        @Override
        public int getNewListSize() {
            return getListSize(mNewList);
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return getListItemData(mOldList, oldItemPosition).equals(getListItemData(mNewList, newItemPosition));
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return false;
        }

        private int getListSize(@Nullable List<T> list) {
            return null == list ? 0 : list.size();
        }

        @Nullable
        private T getListItemData(@Nullable List<T> list, int position) {
            return null == list ? null : list.get(position);
        }
    }
}
