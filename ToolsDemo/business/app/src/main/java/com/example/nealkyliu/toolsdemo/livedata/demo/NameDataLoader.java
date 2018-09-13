package com.example.nealkyliu.toolsdemo.livedata.demo;

import java.util.concurrent.TimeUnit;

/**
 * Created by nealkyliu on 2018/6/23.
 */

public class NameDataLoader {
    private NameBean mData;

    public interface IDataLoaderCallback<T> {
        void onDataLoaded(T data);
    }

    public void loadData(final IDataLoaderCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(10));
                } catch (InterruptedException e) {
                }

                if (null != callback) {
                    callback.onDataLoaded(mData);
                }
            }
        }).start();
    }
}
