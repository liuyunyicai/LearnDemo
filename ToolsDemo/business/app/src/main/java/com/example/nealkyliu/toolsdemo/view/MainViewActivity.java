package com.example.nealkyliu.toolsdemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.example.nealkyliu.toolsdemo.R;
import com.example.nealkyliu.toolsdemo.livedata.activity.AbsActivity;
import com.example.nealkyliu.toolsdemo.utils.LogUtils;
import com.example.nealkyliu.toolsdemo.utils.glide.GlideUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/23
 **/
public class MainViewActivity extends AbsActivity {
    MultiOverlapView mMultiOverlapView;

    List<String> mImageList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d("MainViewActivity", "onCreate");
    }

    @Override
    protected void initView() {
//        mImageList.add("http://p0.ipstatp.com/thumb/005b0cbec95740ad3f3d.webp");
//        mImageList.add("http://p1.ipstatp.com/list/005ba1963f9dc091ece2.webp");
//        mImageList.add("http://p0.ipstatp.com/large/005b7bb4117600a3ec50");
//        mImageList.add("http://p1.ipstatp.com/thumb/005ba12bdd7e80922d74.webp");
//        mMultiOverlapView = findViewById(R.id.mTextOverlapView);
//        mMultiOverlapView.configImageLoadService(new MultiOverlapView.IImageLoadService() {
//            @Override
//            public void bindImage(@NotNull ImageView imageView, @NotNull String url) {
//                GlideUtils.bindImageViewUrl(MainViewActivity.this, url, imageView, 0);
//            }
//        });
//        mMultiOverlapView.setDisplayText("HAHHHSHHSHSHSHSh");
//        mMultiOverlapView.setImages(mImageList);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_view;
    }

    @Override
    protected void initData() {

    }
}
