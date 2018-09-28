package com.example.nealkyliu.toolsdemo.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.view.contains
import androidx.view.get
import com.example.nealkyliu.toolsdemo.R
import com.example.utils.UIUtils

/**
 * @author: Created By nealkyliu
 * @date: 2018/9/18
 *
 **/
class MultiOverlapView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {
    /**
     * max num of overlap images, default DEFAULT_OVIMG_MAX_NUM
     **/
    private var mOvImgMaxNum : Int
    /**
     * the width & height of Ov imageView
     **/
    private var mOvImgSize : Int
    /**
     * overlap length of one ov image overlap another
     **/
    private var mOvImgOverlapLength : Int
    /**
     * display textView left margin size
     **/
    private var mDisplayTvLeftMargin : Int
    /**
     * display textView text size
     **/
    private var mDisplayTvTextSize : Int
    /**
     * display textView text color
     **/
    private var mDisplayTvTextColor : Int

    private var mDisplayTextView: TextView = TextView(context)

    private lateinit var mImageLoadService: IImageLoadService

    init {
        mOvImgMaxNum         = DEFAULT_OVIMG_MAX_NUM
        mOvImgSize           = UIUtils.dip2Px(context, DEFAULT_OVIMG_VIEW_SIZE).toInt()
        mOvImgOverlapLength  = UIUtils.dip2Px(context, DEFAULT_OV_IMG_OVERLAP_LENGTH).toInt()
        mDisplayTvLeftMargin = UIUtils.dip2Px(context, DEFAULT_DISPLAY_TV_LEFT_MARGIN).toInt()
        mDisplayTvTextSize   = UIUtils.sp2px(context, DEFAULT_DISPLAY_TV_TEXT_SIZE).toInt()
        mDisplayTvTextColor  = DEFAULT_DISPLAY_TV_TEXT_COLOR

        parseAttrs(attrs, context, defStyleAttr)
        initView()
        setVerticalGravity(Gravity.CENTER_VERTICAL)
    }

    private fun parseAttrs(attrs: AttributeSet?, context: Context, defStyleAttr: Int) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MultiOverlapView, defStyleAttr, 0)
            mOvImgMaxNum         = typedArray.getInt(R.styleable.MultiOverlapView_maxOvImageNum, mOvImgMaxNum)
            mOvImgSize           = typedArray.getDimensionPixelSize(R.styleable.MultiOverlapView_ovImageSize, mOvImgSize)
            mOvImgOverlapLength  = typedArray.getDimensionPixelSize(R.styleable.MultiOverlapView_ovImgOverlapLength, mOvImgOverlapLength)
            mDisplayTvLeftMargin = typedArray.getDimensionPixelSize(R.styleable.MultiOverlapView_displayTvLeftMargin, mDisplayTvLeftMargin)
            mDisplayTvTextSize   = typedArray.getDimensionPixelSize(R.styleable.MultiOverlapView_displayTvSize, mDisplayTvTextSize)
            mDisplayTvTextColor  = typedArray.getResourceId(R.styleable.MultiOverlapView_displayTvColor, mDisplayTvTextColor)
            typedArray.recycle()
        }
    }

    private fun initView() {
        mDisplayTextView.setTextColor(resources.getColor(mDisplayTvTextColor))
        mDisplayTextView.textSize = mDisplayTvTextSize.toFloat()
    }

    companion object {
        const val DEFAULT_OVIMG_MAX_NUM          = 5
        const val DEFAULT_OVIMG_VIEW_SIZE        = 24   // 单位dp
        const val DEFAULT_OV_IMG_OVERLAP_LENGTH  = 8    // 单位dp
        const val DEFAULT_DISPLAY_TV_LEFT_MARGIN = 8    // 单位dp
        const val DEFAULT_DISPLAY_TV_TEXT_SIZE   = 6   // 单位sp
        const val DEFAULT_DISPLAY_TV_TEXT_COLOR  = R.color.white
    }

    fun setDisplayText(text: String) {
        bindText(mDisplayTextView, text)
    }

    fun setImages(imageUrlList: List<String>) {
        // fill views
        tryAddOvImgs(imageUrlList.size)
        // bind info data
        for (i in 0 until Math.min(getCurrentOvImgNum(), imageUrlList.size)) {
            bindImage(getImageView(i), imageUrlList[i])
        }
    }

    /**
     * should called before setImages
     **/
    fun configImageLoadService(service: IImageLoadService) {
        mImageLoadService = service
    }

    /**
     * @param expectNum expect imageView num
     **/
    private fun tryAddOvImgs(expectNum: Int) {
        val curNum = getCurrentOvImgNum()

        val validExpectNum = Math.min(mOvImgMaxNum, expectNum)

        /**
         * When more than expect, need REMOVE; When less, need ADD
         **/
        if (curNum > validExpectNum) {
            for (i in validExpectNum until curNum) {
                removeOvImg(i)
            }
        } else if (curNum < validExpectNum) {
            for (i in curNum until validExpectNum) {
                addOvImg(i)
            }
            addDisplayTv()
        }
    }

    private fun addOvImg(index: Int) {
        var left = 0
        if (0 != index) {
            left = mOvImgOverlapLength
        }
        val lp = LinearLayout.LayoutParams(mOvImgSize, mOvImgSize)
        lp.leftMargin = -left

        val img = ImageView(context)
        addView(img, lp)
    }

    private fun removeOvImg(index: Int) {
        if (!validOvImageIndex(index)) {
            return
        }

        try {
            removeViewAt(index)
        } catch (e: Throwable) {
        }
    }

    /**
     * when already added, need reAdd
     ***/
    private fun addDisplayTv() {
        if (contains(mDisplayTextView)) {
            removeView(mDisplayTextView)
        }

        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.leftMargin = mDisplayTvLeftMargin
        addView(mDisplayTextView, lp)
    }

    /**
     * Total count = imageView count + one TextView
     * When imageView added, textView is always added
     **/
    private fun getCurrentOvImgNum(): Int {
        return Math.max(childCount - 1, 0)
    }

    private fun getImageView(index: Int): ImageView? {
        if (!validOvImageIndex(index)) {
            return null
        }
        return get(index) as ImageView
    }

    private fun validOvImageIndex(index: Int): Boolean {
        if (index >= getCurrentOvImgNum() || index < 0) {
            return false
        }
        return true
    }

    /**
     * tools
     **/
    private fun bindImage(imageView: ImageView?, url: String) {
        mImageLoadService.bindImage(imageView, url)
    }

    private fun bindText(textView: TextView, text: CharSequence) {
        textView.text = text
    }


    interface IImageLoadService {
        fun bindImage(imageView: ImageView?, url: String)
    }
}
