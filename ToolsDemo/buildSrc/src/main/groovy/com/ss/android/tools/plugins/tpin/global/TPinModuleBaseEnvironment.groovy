package com.ss.android.tools.plugins.tpin.global

import com.ss.android.tools.plugins.tpin.extension.api.ITPinExtension
import com.ss.android.tools.plugins.tpin.global.executors.factory.ExecutorFactory
import com.ss.android.tools.plugins.tpin.global.service.ModuleManagerService
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import org.gradle.api.Project

/**
 * 基础管理类
 **/
class TPinModuleBaseEnvironment {
    Map<String, ITPinExtension> mExtensionMap = new HashMap<>()

    Project mProject

    // 各种基础Task
    ModuleManagerService mModuleManagerService


    TPinModuleBaseEnvironment(Project project) {
        setProject(project)
        mModuleManagerService = new ModuleManagerService(project)
    }


    void setProject(Project project) {
        mProject = project
    }


    void includeModule(String name, String flavor = "main", String rootDir = null, boolean isMain = false) {
        mModuleManagerService.includeModule(name, flavor, rootDir, isMain)
    }


    void clearModules() {
        TPinUtils.logInfo("clearModules")
        clearSourceSet()
        mModuleManagerService.clearModules()
    }

    /**
     * 设置sourceSet
     **/
    void clearSourceSet() {
        TPinUtils.logInfo("clearSourceSet")
        ExecutorFactory.getExecutor(mProject, ExecutorFactory.KEY_MINUS_SOURCESET_EXECUTOR).execute(mModuleManagerService)
    }

    void setSourceSet() {
        TPinUtils.logInfo("setSourceSet")
        ExecutorFactory.getExecutor(mProject, ExecutorFactory.KEY_SET_SOURCESET_EXECUTOR).execute(mModuleManagerService)
    }

    /**
     * 执行module的build文件
     **/
    void applyModuleBuild() {
        TPinUtils.logInfo("applyModuleBuild")
        ExecutorFactory.getExecutor(mProject, ExecutorFactory.KEY_APPLY_BUILD_EXECUTOR).execute(mModuleManagerService)
    }

    /**
     * 生成R文件
     **/
    void generateR() {
        ExecutorFactory.getExecutor(mProject, ExecutorFactory.KEY_GENERATE_R_EXECUTOR).execute(mModuleManagerService)
    }

    /**
     * merge manifest文件
     **/
    void mergeManifest() {
        ExecutorFactory.getExecutor(mProject, ExecutorFactory.KEY_MERGE_MANIFEST_EXECUTOR).execute(mModuleManagerService)
    }

    /***
     * 生成code-check.xml
     **/
    void writeCodeCheckFile() {
        ExecutorFactory.getExecutor(mProject, ExecutorFactory.KEY_WRITE_CODE_CHECK_FILE_EXECUTOR).execute(mModuleManagerService)
    }


    void finish() {
        mGlobalEnv     = null
        mProject       = null
    }

}