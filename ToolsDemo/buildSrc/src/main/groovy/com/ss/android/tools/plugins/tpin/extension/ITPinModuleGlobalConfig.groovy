package com.ss.android.tools.plugins.tpin.extension

interface ITPinModuleGlobalConfig {
    /**
     * 配置生成Mainfest的文件夹位置
     **/
    void outputManifestDir(String dir)

    /**
     * 配置生成R文件的位置
     **/
    void outputRFileDir(String dir)

}