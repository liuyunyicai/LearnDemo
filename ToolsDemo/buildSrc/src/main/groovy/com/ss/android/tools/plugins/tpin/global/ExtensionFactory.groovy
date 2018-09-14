package com.ss.android.tools.plugins.tpin.global

import com.android.build.gradle.BaseExtension
import com.ss.android.tools.plugins.tpin.extension.ITPinModuleConfigExtension
import com.ss.android.tools.plugins.tpin.extension.ITPinModuleExtension
import com.ss.android.tools.plugins.tpin.extension.TPinModuleConfigExtensionImpl
import com.ss.android.tools.plugins.tpin.extension.TPinModuleExtensionImpl
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import org.gradle.api.Project

class ExtensionFactory {

    static <T> T build(Project project, String name) {
        Class<?> api
        Class<?> impl
        switch (name) {
            case ITPinModuleExtension.NAME:
                api = ITPinModuleExtension.class
                impl = TPinModuleExtensionImpl.class
                break
            case ITPinModuleConfigExtension.NAME:
                api = ITPinModuleConfigExtension.class
                impl = TPinModuleConfigExtensionImpl.class
                break
        }

        if (null != api && null != impl) {
            return project.extensions.create(api, name, impl, project)
        }
        TPinUtils.throwException("Invaid Extension Name ", name)
    }

    static BaseExtension getBaseExtension(Project project, String name) {
        return project.extensions.getByName(name)
    }
}