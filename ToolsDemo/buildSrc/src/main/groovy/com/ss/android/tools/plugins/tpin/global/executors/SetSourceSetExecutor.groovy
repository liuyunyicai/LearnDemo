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
 * 设置SourceSet
 **/
class SetSourceSetExecutor extends BaseExecutor{

    SetSourceSetExecutor(Project project) {
        super(project)
    }

    @Override
    void execute(IExecutorContext context) {
        includeIntoSourceSet(context.project, context.pinModules.iterator())
    }
/**
     * TODO: To optimize
     * 添加到SourceSet中
     **/
    void includeIntoSourceSet(Project project, TPinModuleModel... modules) {
        includeIntoSourceSet(project, TPinUtils.variableParamsToList(modules))
    }

    void includeIntoSourceSet(Project project, Iterator<TPinModuleModel> modules) {

        modules.each {
            def set = it.mAndroidSourceSet
            it.mFlavors.each { flavor ->
                AndroidSourceSet obj = getProjectSourceSet(project, flavor)
                plusSourcesSet(obj.java        , set.java)
                plusSourcesSet(obj.res         , set.res)
                plusSourcesSet(obj.jni         , set.jni)
                plusSourcesSet(obj.jniLibs     , set.jniLibs)
                plusSourcesSet(obj.aidl        , set.aidl)
                plusSourcesSet(obj.assets      , set.assets)
                plusSourcesSet(obj.shaders     , set.shaders)
                plusSourcesSet(obj.resources   , set.resources)
                plusSourcesSet(obj.renderscript, set.renderscript)
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

    void plusSourcesSet(AndroidSourceDirectorySet origin, AndroidSourceDirectorySet add) {
//        TPinUtils.logInfo("**** origin ", origin.srcDirs, "add ", add.srcDirs)

        add.srcDirs.each {
            origin.srcDirs += TPinGradleUtils.getAbsolutePath(mProject, it.absolutePath)
        }

    }
}