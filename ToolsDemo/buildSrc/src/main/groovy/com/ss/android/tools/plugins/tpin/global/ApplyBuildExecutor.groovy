package com.ss.android.tools.plugins.tpin.global

import com.ss.android.tools.plugins.tpin.common.TPinModuleConstants
import com.ss.android.tools.plugins.tpin.global.base.BaseExecutor
import com.ss.android.tools.plugins.tpin.module.TPinModuleModel
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import org.gradle.api.Project

/**
 * 执行module的build文件
 **/
class ApplyBuildExecutor extends BaseExecutor{

    ApplyBuildExecutor(Project project) {
        super(project)
    }

    void execute(Iterable<TPinModuleModel> modules) {
        modules.each {
            applyPinModuleBuild(it)
        }
    }

    /**
     * 执行每一个module的build.gradle
     **/
    void applyPinModuleBuild(TPinModuleModel pinModule) {
        def pinModuleBuild = TPinUtils.createFile(mProject, pinModule.mRootDir, TPinModuleConstants.BUILD_GRADLE_NAME)

        TPinUtils.logInfo("applyPinModuleBuild", pinModuleBuild.absolutePath)

        if (pinModuleBuild.exists()) {
            TPinModuleEnvironment.getInstance(mProject).saveValue("myConfig", pinModule.mName)
            mProject.apply from: pinModuleBuild.absolutePath
        }
    }
}