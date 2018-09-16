package com.ss.android.tools.plugins.tpin.extension.api

import com.android.build.gradle.api.AndroidSourceSet
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer

/**
 * 设置当前module的sourceSet
 **/
interface ITPinModuleConfigExtension extends ITPinExtension {
    String NAME = "tpinConfig"

    void sourceSet(Action<AndroidSourceSet> action)

    void testSourceSets(Action<NamedDomainObjectContainer<AndroidSourceSet>> action)
}