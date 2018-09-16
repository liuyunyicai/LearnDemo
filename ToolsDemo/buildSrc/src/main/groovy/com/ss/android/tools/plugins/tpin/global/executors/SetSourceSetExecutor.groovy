package com.ss.android.tools.plugins.tpin.global.executors

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.api.AndroidSourceDirectorySet
import com.android.build.gradle.api.AndroidSourceSet
import com.ss.android.tools.plugins.tpin.global.ExtensionFactory
import com.ss.android.tools.plugins.tpin.global.base.BaseExecutor
import com.ss.android.tools.plugins.tpin.module.TPinModuleModel
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

/**
 * 设置SourceSet
 **/
class SetSourceSetExecutor extends BaseExecutor{

    SetSourceSetExecutor(Project project) {
        super(project)

    }

    /**
     * TODO: To optimize
     * 添加到SourceSet中
     **/
    void includeIntoSourceSet(Project project, TPinModuleModel... sourceSets) {
        includeIntoSourceSet(project, TPinUtils.variableParamsToList(sourceSets))
    }

    void includeIntoSourceSet(Project project, Iterator<TPinModuleModel> sourceSets) {

        sourceSets.each {
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
     * 从sourceSet中移除
     **/
    void excludeFromSourceSet(Project project, TPinModuleModel... sourceSets) {
        excludeFromSourceSet(project, TPinUtils.variableParamsToList(sourceSets))
    }

    void excludeFromSourceSet(Project project, Iterator<TPinModuleModel> sourceSets) {
        sourceSets.each {
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


    void setSourcesSet(AndroidSourceDirectorySet set, List<String> dirs) {
        dirs.each {
            set.srcDir(it)
        }
    }

    void plusSourcesSet(AndroidSourceDirectorySet origin, AndroidSourceDirectorySet add) {
        origin.srcDirs += add.srcDirs
    }

    void minusSourcesSet(AndroidSourceDirectorySet origin, AndroidSourceDirectorySet minus) {
        origin.srcDirs -= minus.srcDirs
    }
}