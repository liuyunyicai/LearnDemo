package com.ss.android.tools.plugins.tpin.global.executors

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.api.AndroidSourceDirectorySet
import com.android.build.gradle.api.AndroidSourceSet
import com.ss.android.tools.plugins.tpin.extension.factory.ExtensionFactory
import com.ss.android.tools.plugins.tpin.global.executors.base.BaseExecutor
import com.ss.android.tools.plugins.tpin.global.executors.context.IExecutorContext
import com.ss.android.tools.plugins.tpin.model.TPinModuleModel
import com.ss.android.tools.plugins.tpin.utils.TPinGradleUtils
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import org.gradle.api.Project

/**
 * 清除设置的SourceSet
 **/
class MinusSourceSetExecutor extends BaseExecutor{

    MinusSourceSetExecutor(Project project) {
        super(project)
    }

    @Override
    void execute(IExecutorContext context) {
        excludeFromSourceSet(context.project, context.pinModules.iterator())
    }

    /**
     * 从sourceSet中移除
     **/
    void excludeFromSourceSet(Project project, TPinModuleModel... modules) {
        excludeFromSourceSet(project, TPinUtils.variableParamsToList(modules))
    }

    void excludeFromSourceSet(Project project, Iterator<TPinModuleModel> modules) {
        modules.each {
            def set = it.mAndroidSourceSet
            it.mFlavors.each { flavor ->
                AndroidSourceSet obj = getProjectSourceSet(project, flavor)
                minusSourcesSet(obj.java, set.java)
                minusSourcesSet(obj.res, set.res)
                minusSourcesSet(obj.jni, set.jni)
                minusSourcesSet(obj.jniLibs, set.jniLibs)
                minusSourcesSet(obj.aidl, set.aidl)
                minusSourcesSet(obj.assets, set.assets)
                minusSourcesSet(obj.shaders, set.shaders)
                minusSourcesSet(obj.resources, set.resources)
                minusSourcesSet(obj.renderscript, set.renderscript)
            }
        }
    }


    /**
     * 获取当前Project SourceSet
     **/
    AndroidSourceSet getProjectSourceSet(Project project, String flavor = "main") {
        BaseExtension android = ExtensionFactory.getBaseExtension(project, "android")
        def sourceSet = android.sourceSets.getByName(flavor)
        return sourceSet
    }


    void minusSourcesSet(AndroidSourceDirectorySet origin, AndroidSourceDirectorySet minus) {
        minus.srcDirs.each {
            origin.srcDirs -= TPinGradleUtils.getAbsolutePath(mProject, it.absolutePath)
        }
    }
}