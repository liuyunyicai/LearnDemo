package com.example.nealkyliu.toolsdemo.kotlin

import android.view.View
import com.example.nealkyliu.toolsdemo.R.id.mGoLifeCycle
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by nealkyliu on 2018/7/1.
 */
open class ClassA{

    open fun getUnit() = "string"


    internal companion object {
        fun getStaticMethod() : String = "jajjaj"
    }

    fun aaaa() {
//        mGoLifeCycle.setText("")
    }
}