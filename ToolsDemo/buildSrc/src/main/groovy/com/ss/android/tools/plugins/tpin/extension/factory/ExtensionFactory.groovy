package com.ss.android.tools.plugins.tpin.extension.factory

import com.android.build.gradle.BaseExtension
import com.ss.android.tools.plugins.tpin.extension.TPinModuleWithFlavorExtensionImpl
import com.ss.android.tools.plugins.tpin.extension.api.ITPinModuleConfigExtension
import com.ss.android.tools.plugins.tpin.extension.api.ITPinModuleWithFlavorExtension
import com.ss.android.tools.plugins.tpin.extension.TPinModuleConfigExtensionImpl
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import org.gradle.api.Project

class ExtensionFactory {

    /**
     * 自定义extension
     **/
    static <T> T build(Project project, String name) {
        Class<?> api
        Class<?> impl
        switch (name) {
            case ITPinModuleWithFlavorExtension.NAME:
                api = ITPinModuleWithFlavorExtension.class
                impl = TPinModuleWithFlavorExtensionImpl.class
                break
            case ITPinModuleConfigExtension.NAME:
                api = ITPinModuleConfigExtension.class
                impl = TPinModuleConfigExtensionImpl.class
                break
        }

        if (null != api && null != impl) {
            return project.extensions.create(api, name, impl, project)
        }
        TPinUtils.throwException("Invalid Extension Name ", name)
    }

    /**
     * 获取基础Extension, 如android
     **/
    static BaseExtension getBaseExtension(Project project, String name) {
        return project.extensions.getByName(name)
    }

    static BaseExtension getAndroidExtension(Project project) {
        return project.extensions.getByName('android')
    }
}