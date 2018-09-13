package com.example.nealkyliu.toolsdemo.kotlin

import com.example.nealkyliu.toolsdemo.utils.LogUtils
import javax.inject.Singleton

/**
 * Created by nealkyliu on 2018/7/1.
 */
@Singleton
class ClassC {
    fun test() {
        com.example.nealkyliu.toolsdemo.kotlin.ClassB().getUnit()
        ClassA.getStaticMethod()
        LogUtils.d(this, "ClassC test")
    }
}