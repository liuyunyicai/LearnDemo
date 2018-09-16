package com.ss.android.tools.plugins.tpin.module

import org.gradle.api.Project

/**
 * 环境变量配置
 **/
class GlobalEnviModel {

    /**
     * 生成manifest文件位置
     **/
    String mMainfestDir

    /**
     * 生成RFile文件位置
     **/
    String mRFileDir

    GlobalEnviModel() {
        mMainfestDir = "/build/tpinModule/"
        mRFileDir = ""
    }
}