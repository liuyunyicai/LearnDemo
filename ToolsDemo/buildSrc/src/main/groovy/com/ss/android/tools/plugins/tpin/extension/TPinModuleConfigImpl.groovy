package com.ss.android.tools.plugins.tpin.extension

import com.ss.android.tools.plugins.tpin.extension.api.ITPinModuleConfig
import com.ss.android.tools.plugins.tpin.global.TPinModuleEnvironment
import org.gradle.api.Project

class TPinModuleConfigImpl extends BaseTPinExtensionImpl implements ITPinModuleConfig {
    // 这里的name是flavor的概念
    String name

    TPinModuleConfigImpl(Project project, String flavor) {
        super(project)
        name = flavor
    }

    @Override
    void include(String moduleName) {
        TPinModuleEnvironment.getInstance(mProject).includeModule(moduleName, name)
    }

    @Override
    void include(String moduleName, String rootDir) {
        TPinModuleEnvironment.getInstance(mProject).includeModule(moduleName, name, rootDir)
    }

    @Override
    void include(String moduleName, String rootDir, String codeRootDir) {
        TPinModuleEnvironment.getInstance(mProject).includeModule(moduleName, name, rootDir, codeRootDir)
    }

    @Override
    void includemain(String moduleName) {
        TPinModuleEnvironment.getInstance(mProject).includeModule(moduleName, name, isMain = true)
    }

    @Override
    void includemain(String moduleName, String rootDir) {
        TPinModuleEnvironment.getInstance(mProject).includeModule(moduleName, name, rootDir, true)
    }

    @Override
    void includemain(String moduleName, String rootDir, String codeRootDir) {
        TPinModuleEnvironment.getInstance(mProject).includeModule(moduleName, name, rootDir, true, codeRootDir)
    }
}