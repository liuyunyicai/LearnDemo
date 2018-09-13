package com.example.nealkyliu.toolsdemo.livedata.fragment;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nealkyliu.toolsdemo.R;
import com.example.nealkyliu.toolsdemo.livedata.ExtendedViewModelProviders;
import com.example.nealkyliu.toolsdemo.livedata.demo.NameBean;
import com.example.nealkyliu.toolsdemo.livedata.demo.NameViewModel;
import com.example.nealkyliu.toolsdemo.utils.LogUtils;
import com.example.nealkyliu.toolsdemo.utils.glide.GlideUtils;

/**
 * Created by nealkyliu on 2018/6/25.
 */

public abstract class AbsNameFragment extends Fragment implements View.OnClickListener{
    private NameViewModel mModel;

    private TextView mTextView;
    private ImageView mGlideImage;

    private static final String[] mImageUrls = new String[]{"http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg",
            "http://pic.58pic.com/58pic/17/14/79/66858PIChCy_1024.jpg", "http://p0.ipstatp.com/list/640x360/f05a91302b0dc0a5dd07",
                                                "http://p0.ipstatp.com/list/640x360/f05a91302b3a00a52cde", "http://p0.ipstatp.com/large/005aadaa8415c0964c6a.webp"};

    private int mImageUrlIndex = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_name_fragment1, container, false);
        mTextView = view.findViewById(R.id.fragment_textview);
        mGlideImage = view.findViewById(R.id.mGlideImage);
        mTextView.setText(getInitText());

        LogUtils.d(this, "onCreateView");
        initData();

        return view;
    }

    private void initData() {
        // Get the ViewModel.
        mModel = ExtendedViewModelProviders.of(getActivity()).get(NameViewModel.class);

        // Create the observer which updates the UI.
        final Observer<NameBean> nameObserver = new Observer<NameBean>() {
            @Override
            public void onChanged(@Nullable final NameBean newName) {
                // Update the UI, in this case, a TextView.
                LogUtils.d(this, "onChanged " + newName);
                if (null != newName) {
                    mTextView.setText(getInitText() + newName.toString());
                }
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mModel.getCurrentName().observe(this, nameObserver);

        mTextView.setOnClickListener(this);
        mGlideImage.setOnClickListener(this);
    }

    protected String getInitText() {
        return getClass().getSimpleName();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_textview: {
                NameBean value = mModel.getCurrentName().getValue();
                if (value == null) {
                    value = new NameBean();
                }
                value.count++;

                mModel.getCurrentName().setValue(value);
            }
            break;
            case R.id.mGlideImage: {
                String url = mImageUrls[mImageUrlIndex % mImageUrls.length];
                mImageUrlIndex++;
                GlideUtils.bindImageViewUrl(getContext(), url, mGlideImage, 0);
            }
            break;
        }
    }
}
