package com.ss.android.tools.plugins.tpin.model
/**
 * 环境变量配置
 * 此文件中都是相对路径
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

    /**
     * 生成code check位置
     **/
    String mCodeCheckDir

    GlobalEnviModel() {
        mMainfestDir = "/build/tpinModule/"
        mCodeCheckDir= "/build/tpinModule/codecheck"
        mRFileDir = ""
    }
}