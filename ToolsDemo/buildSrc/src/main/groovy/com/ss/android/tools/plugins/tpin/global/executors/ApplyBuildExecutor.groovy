package com.ss.android.tools.plugins.tpin.global.executors

import com.ss.android.tools.plugins.tpin.common.TPinModuleConstants
import com.ss.android.tools.plugins.tpin.global.TPinModuleEnvironment
import com.ss.android.tools.plugins.tpin.global.executors.base.BaseExecutor
import com.ss.android.tools.plugins.tpin.global.service.context.IExecutorContext
import com.ss.android.tools.plugins.tpin.model.TPinModuleModel
import com.ss.android.tools.plugins.tpin.utils.TPinGradleUtils
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import org.gradle.api.Project

/**
 * 执行module的build文件
 **/
class ApplyBuildExecutor extends BaseExecutor{
    ApplyBuildExecutor(Project project) {
        super(project)
    }

    @Override
    void execute(IExecutorContext context) {
        context.curFlavorPinModules.each {
            applyPinModuleBuild(context, it)
        }
        context.setCurrentApplyModel(null)
    }


    /**
     * 执行每一个module的build.gradle
     **/
    void applyPinModuleBuild(IExecutorContext context, TPinModuleModel pinModule) {
        def pinModuleBuild = TPinGradleUtils.createFile(mProject, pinModule.mRootDir, TPinModuleConstants.BUILD_GRADLE_NAME)

        TPinUtils.logInfo("applyPinModuleBuild", pinModule.mName, pinModuleBuild.absolutePath)

        if (pinModuleBuild.exists()) {
            TPinModuleEnvironment.getInstance(mProject).saveValue("myConfig", pinModule.mName)
            context.setCurrentApplyModel(pinModule)
            mProject.apply from: pinModuleBuild.absolutePath
        }
    }
}