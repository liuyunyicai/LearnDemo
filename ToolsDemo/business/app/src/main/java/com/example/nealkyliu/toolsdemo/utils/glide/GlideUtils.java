package com.example.nealkyliu.toolsdemo.utils.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by nealkyliu on 2018/7/4.
 */

public class GlideUtils {
    public static void bindImageViewUrl(final Context context, final String url, final ImageView imageView, final int placeHolder) {
        Glide.with(context)
                .load(url)
                .placeholder(imageView.getDrawable())
                .crossFade()
                .skipMemoryCache(false)
                .into(imageView);
    }
}
