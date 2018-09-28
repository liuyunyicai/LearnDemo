package com.example.nealkyliu.toolsdemo.bussiness.test.job.impl

import com.example.nealkyliu.toolsdemo.bussiness.test.job.IJob
import com.example.nealkyliu.toolsdemo.utils.DebugLogger

/**
 * @author: Created By nealkyliu
 * @date: 2018/9/18
 **/
class TestJob4 : IJob{

    val SYNTHETIC_PACKAGE: String = "kotlinx.android.synthetic"
    val SYNTHETIC_PACKAGE_PATH_LENGTH = SYNTHETIC_PACKAGE.count { it == '.' } + 1

    val SYNTHETIC_SUBPACKAGES: List<String> = SYNTHETIC_PACKAGE.split('.').fold(arrayListOf<String>()) { list, segment ->
        val prevSegment = list.lastOrNull()?.let { "$it." } ?: ""
        list += "$prevSegment$segment"
        list
    }

    var mInnerClass  = InnerClass()

    override fun run() {
        mInnerClass = InnerClass()
        test()
    }

    fun test() {
        mInnerClass.name = ""
        DebugLogger.d("TestJob4 END", SYNTHETIC_SUBPACKAGES)


    }

    class InnerClass  {
        lateinit var name : String
    }
}

interface ITestJobInterface {
    fun returnValue()
}