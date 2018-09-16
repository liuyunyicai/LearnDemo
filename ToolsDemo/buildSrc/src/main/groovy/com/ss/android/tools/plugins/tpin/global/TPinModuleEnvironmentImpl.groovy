package com.ss.android.tools.plugins.tpin.global

import com.android.build.gradle.BaseExtension
import com.ss.android.tools.plugins.tpin.extension.api.ITPinExtension
import com.ss.android.tools.plugins.tpin.extension.api.ITPinModuleConfigExtension
import com.ss.android.tools.plugins.tpin.extension.api.ITPinModuleConfig
import com.ss.android.tools.plugins.tpin.extension.api.ITPinModuleWithFlavorExtension
import com.ss.android.tools.plugins.tpin.module.TPinModuleModel
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import org.gradle.api.Project

import javax.annotation.Nullable

/**
 * 用来管理当前build task中添加的modules； 以及便于日后的增量编译
 **/
class TPinModuleEnvironmentImpl extends TPinModuleBaseEnvironment{
    Map<String, Object> mSavedKeys = new HashMap<>()


    TPinModuleEnvironmentImpl(Project project) {
        super(project)
        initExtensions()
        initTasks()
    }

    /**/
    void initExtensions() {
        getPinModuleExtension()
        getPinModuleConfigExtension()
    }

    /**
     * 创建具体任务
     * TODO:Optimzie
     **/
    void initTasks() {
        mProject.afterEvaluate {
            TPinUtils.logInfo("afterEvaluate")
            // apply Build
            applyModuleBuild()
            // 设置Task
            setSourceSet()
            // merge Manifest
            mergeManifest()
        }
    }

    /**
     * 构建tpinModule DSL
     **/
    ITPinModuleWithFlavorExtension getPinModuleExtension() {
        return getExtension(ITPinModuleWithFlavorExtension.NAME)
    }

    /**
     * 构建tpinConfig sourceSet
     **/
    ITPinModuleConfigExtension getPinModuleConfigExtension() {
        return getExtension(ITPinModuleConfigExtension.NAME)
    }


    BaseExtension getBaseExtension(String name) {
        return ExtensionFactory.getBaseExtension(mProject, name)
    }

    private <T> T getExtension(String name) {
        ITPinExtension extension = mExtensionMap.get(name)

        if (null == extension) {
            extension = ExtensionFactory.build(mProject, name)
            mExtensionMap.put(name, extension)
        }
        return (T) extension
    }

    /**
     * 当前执行build.gradle所在的module
     **/
    @Nullable
    TPinModuleModel getCurrentApplyModule() {
        return mApplyBuildExecutor.mCurrentApplyModule
    }

    void saveValue(String key, Object value) {
        mSavedKeys.put(key, value)
    }

    Object getValue(String key) {
        return mSavedKeys.get(key)
    }
}