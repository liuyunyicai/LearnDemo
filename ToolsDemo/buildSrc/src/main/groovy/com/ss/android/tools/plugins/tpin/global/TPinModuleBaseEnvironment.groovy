package com.ss.android.tools.plugins.tpin.global

import com.android.build.gradle.api.AndroidSourceDirectorySet
import com.ss.android.tools.plugins.tpin.extension.ITPinExtension
import com.ss.android.tools.plugins.tpin.module.GlobalEnviModel
import com.ss.android.tools.plugins.tpin.module.TPinModuleModel
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import org.gradle.api.Project

/**
 * 基础管理类
 **/
class TPinModuleBaseEnvironment {
    Map<String, ITPinExtension> mExtensionMap = new HashMap<>()

    GlobalEnviModel mGlobalEnviModel

    TPinModuleModel mMainPinModule

    Map<String, TPinModuleModel> mPinModulesMap = new HashMap<>()

    Project mProject

    // 各种基础Task
    ModuleManagerExecutor mModuleManagerExecutor
    ApplyBuildExecutor    mApplyBuildExecutor
    GenerateRExecutor     mGenerateRExecutor
    MergeManifestExecutor mMergeManifestExecutor
    SetSourceSetExecutor  mSetSourceSetExecutor


    TPinModuleBaseEnvironment(Project project) {
        setProject(project)
        mModuleManagerExecutor = new ModuleManagerExecutor(project)
        mApplyBuildExecutor    = new ApplyBuildExecutor(project)
        mGenerateRExecutor     = new GenerateRExecutor(project)
        mMergeManifestExecutor = new MergeManifestExecutor(project)
        mSetSourceSetExecutor  = new SetSourceSetExecutor(project)
    }


    void setProject(Project project) {
        mProject = project
    }


    void includeModule(String name, String rootDir = null, boolean isMain = false) {
        mModuleManagerExecutor.includeModule(name, rootDir, isMain)
    }


    void clearModules() {
        TPinUtils.logInfo("clearModules")
        clearSourceSet()
        mModuleManagerExecutor.clearModules()
    }

    /**
     * 设置sourceSet
     **/
    void clearSourceSet() {
        TPinUtils.logInfo("clearSourceSet")
        mSetSourceSetExecutor.excludeFromSourceSet(mProject, mModuleManagerExecutor.pinModules)
    }

    void setSourceSet() {
        TPinUtils.logInfo("setSourceSet")
        mSetSourceSetExecutor.includeIntoSourceSet(mProject, mModuleManagerExecutor.pinModules)
    }

    /**
     * 执行module的build文件
     **/
    void applyModuleBuild() {
        TPinUtils.logInfo("applyModuleBuild")
        mApplyBuildExecutor.execute(mModuleManagerExecutor.pinModules)
    }

    /**
     * 生成R文件
     **/
    void generateR() {

    }

    /**
     * merge manifest文件
     **/
    void mergeManifest() {

    }

    void setSourcesSet(AndroidSourceDirectorySet set, List<String> dirs) {
        dirs.each {
            set.srcDir(it)
        }
    }

    void finish() {
        mGlobalEnv     = null
        mProject       = null
    }

}