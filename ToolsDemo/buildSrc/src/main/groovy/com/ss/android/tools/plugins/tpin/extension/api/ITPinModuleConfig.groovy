#!/usr/bin/groovy
package com.ss.android.tools.plugins.tpin.extension.api

import com.ss.android.tools.plugins.tpin.extension.TPinModuleGlobalConfig
import org.gradle.api.Action

interface ITPinModuleConfig extends ITPinExtension {

    /**
     * 引入普通module
     **/
    void include(String moduleName)

    /**
     * @param rootDir 当前pinModule build.gradle所在的目录
     **/
    void include(String moduleName, String rootDir)

    /**
     * 指定main module
     **/
    void includemain(String moduleName)

    /**
     *
     **/
    void includemain(String moduleName, String rootDir)

    /**
     * 配置整个工程
     **/

}