package com.example.nealkyliu.toolsdemo.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.nealkyliu.toolsdemo.utils.glide.GlideUtils

/**
 * @author: Created By nealkyliu
 * @date: 2018/9/18
 *
 **/
class MultiOverTestlapView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): FrameLayout (context, attrs, defStyleAttr) {

    /**
     * max num of overlap images, default DEFAULT_OVIMG_MAX_NUM
     **/
    private var mImgMxNum = DEFAULT_IMAGE_MAX_NUM

    /**
     *
     **/
    private var mImageViewSize = DEFAULT_IMAGE_VIEW_SIZE

    private var mImageViewLeftMargin = DEFAULT_IMAGE_VIEW_LEFT_MARGIN

    private var mTextViewLeftMargin = DEFAULT_TEXT_VIEW_LEFT_MARGIN

    private var mDisplayTextView : TextView = TextView(context)

    private var mImageViewPool : MutableList<ImageView> = ArrayList()

    init {
        addView(mDisplayTextView, FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT))
    }

    companion object {
        const val DEFAULT_IMAGE_MAX_NUM = 5
        const val DEFAULT_IMAGE_VIEW_SIZE = 24
        const val DEFAULT_IMAGE_VIEW_LEFT_MARGIN = 16
        const val DEFAULT_TEXT_VIEW_LEFT_MARGIN = 8
    }

    fun setDisplayText(text : String) {
        bindText(mDisplayTextView, text)
    }

    fun setImages(imageUrlList : List<String>) {
        tryAddImageView(imageUrlList.size)
        for (i in 0 until Math.min(mImageViewPool.size, imageUrlList.size) - 1) {
           bindImage(mImageViewPool[i], imageUrlList[i])
        }
    }

    private fun bindImage(imageview : ImageView, url : String) {
        GlideUtils.bindImageViewUrl(context, url, imageview, 0)
    }

    private fun bindText(textView : TextView, text : CharSequence) {
        textView.text = text
    }

    private fun tryAddImageView(expectNum : Int) {
        val curNum = mImageViewPool.size

        if (curNum >= mImgMxNum || curNum >= expectNum) {
            return
        }
        for (i in curNum until expectNum - 1) {
            addImageView(i)
        }
        addTextView()
    }

    private fun addImageView(index : Int) {
        val left = mImageViewLeftMargin * index
        val lp = FrameLayout.LayoutParams(mImageViewSize, mImageViewSize)
        lp.leftMargin = left

        val img = ImageView(context)
        addView(img, lp)
        mImageViewPool.add(img)
    }

    private fun addTextView() {
        removeView(mDisplayTextView)
        val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        lp.leftMargin = mImageViewPool.size * mImageViewLeftMargin + mTextViewLeftMargin
        addView(mDisplayTextView, lp)
    }
}