package com.ss.android.tools.plugins.tpin.extension.api

import com.ss.android.tools.plugins.tpin.extension.TPinModuleConfigImpl
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer

/**
 * 对flavor进行配置
 **/
interface ITPinModuleWithFlavorExtension extends ITPinModuleGlobalConfig{
    String NAME = "tpinModules"

    void config(Action<NamedDomainObjectContainer<TPinModuleConfigImpl>> action)

    void debuggable(boolean debug)
}