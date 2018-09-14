#!/usr/bin/groovy
package com.ss.android.tools.plugins.tpin.extension

import org.gradle.api.Action

interface ITPinModuleExtension extends ITPinExtension {

    String NAME = "tpinModules"

    /**
     * 引入普通module
     **/
    void include(String name)

    /**
     * @param rootDir 当前pinModule build.gradle所在的目录
     **/
    void include(String name, String rootDir)

    /**
     * 指定main module
     **/
    void includemain(String name)

    /**
     *
     **/
    void includemain(String name, String rootDir)

    /**
     * 配置整个工程
     **/
    void config(Action<TPinModuleGlobalConfig> action)

}