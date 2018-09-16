package com.ss.android.tools.plugins.tpin.global

import com.android.build.gradle.api.AndroidSourceDirectorySet
import com.ss.android.tools.plugins.tpin.extension.api.ITPinExtension
import com.ss.android.tools.plugins.tpin.global.executors.ApplyBuildExecutor
import com.ss.android.tools.plugins.tpin.global.executors.GenerateRExecutor
import com.ss.android.tools.plugins.tpin.global.executors.MergeManifestExecutor
import com.ss.android.tools.plugins.tpin.global.executors.ModuleManagerExecutor
import com.ss.android.tools.plugins.tpin.global.executors.SetSourceSetExecutor
import com.ss.android.tools.plugins.tpin.global.executors.WriteCodeCheckFileExecutor
import com.ss.android.tools.plugins.tpin.module.GlobalEnviModel
import com.ss.android.tools.plugins.tpin.module.TPinModuleModel
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import com.sun.istack.Nullable
import org.gradle.api.Project

/**
 * 基础管理类
 **/
class TPinModuleBaseEnvironment {
    Map<String, ITPinExtension> mExtensionMap = new HashMap<>()

    Project mProject

    // 各种基础Task
    ModuleManagerExecutor mModuleManagerExecutor
    ApplyBuildExecutor mApplyBuildExecutor
    GenerateRExecutor mGenerateRExecutor
    MergeManifestExecutor mMergeManifestExecutor
    SetSourceSetExecutor mSetSourceSetExecutor
    WriteCodeCheckFileExecutor mWriteCodeCheckFileExecutor


    TPinModuleBaseEnvironment(Project project) {
        setProject(project)
        mModuleManagerExecutor = new ModuleManagerExecutor(project)
        mApplyBuildExecutor    = new ApplyBuildExecutor(project)
        mGenerateRExecutor     = new GenerateRExecutor(project)
        mMergeManifestExecutor = new MergeManifestExecutor(project)
        mSetSourceSetExecutor  = new SetSourceSetExecutor(project)
        mWriteCodeCheckFileExecutor = new WriteCodeCheckFileExecutor(project)
    }


    void setProject(Project project) {
        mProject = project
    }


    void includeModule(String name, String flavor = "main", String rootDir = null, boolean isMain = false) {
        mModuleManagerExecutor.includeModule(name, flavor, rootDir, isMain)
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
        mSetSourceSetExecutor.excludeFromSourceSet(mProject, pinModules)
    }

    void setSourceSet() {
        TPinUtils.logInfo("setSourceSet")
        TPinUtils.logInfo("setSourceSet 2" + mModuleManagerExecutor.pinModules)
        mSetSourceSetExecutor.includeIntoSourceSet(mProject, pinModules)
    }

    /**
     * 执行module的build文件
     **/
    void applyModuleBuild() {
        TPinUtils.logInfo("applyModuleBuild")
        mApplyBuildExecutor.execute(pinModules)
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
        mMergeManifestExecutor.execute(mModuleManagerExecutor.mGlobalEnviModel, pinModules, mainModule)
    }

    /***
     * 生成code-check.xml
     **/
    void writeCodeCheckFile() {
        mWriteCodeCheckFileExecutor.execute(mModuleManagerExecutor.mGlobalEnviModel, pinModules)
    }

    Iterator<TPinModuleModel> getPinModules() {
        return mModuleManagerExecutor.pinModules
    }

    @Nullable
    TPinModuleModel getMainModule() {
        return mModuleManagerExecutor.mainModule
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