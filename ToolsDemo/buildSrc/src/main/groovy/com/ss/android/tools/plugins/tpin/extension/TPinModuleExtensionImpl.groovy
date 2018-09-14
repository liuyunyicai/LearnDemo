package com.ss.android.tools.plugins.tpin.extension

import com.ss.android.tools.plugins.tpin.global.TPinModuleEnvironment
import com.ss.android.tools.plugins.tpin.global.TPinModuleEnvironmentImpl
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import org.gradle.api.Action
import org.gradle.api.Project

class TPinModuleExtensionImpl extends BaseTPinExtensionImpl implements ITPinModuleExtension {


    TPinModuleExtensionImpl(Project project) {
        super(project)
    }

    @Override
    void include(String name) {
        TPinModuleEnvironment.getInstance(mProject).includeModule(name)
    }

    @Override
    void include(String name, String rootDir) {
        TPinModuleEnvironment.getInstance(mProject).includeModule(name, rootDir)
    }

    @Override
    void includemain(String name) {
        TPinModuleEnvironment.getInstance(mProject).includeModule(name, isMain = true)
    }

    @Override
    void includemain(String name, String rootDir) {
        TPinModuleEnvironment.getInstance(mProject).includeModule(name, rootDir, true)
    }

    @Override
    void config(Action<TPinModuleGlobalConfig> action) {

    }
}