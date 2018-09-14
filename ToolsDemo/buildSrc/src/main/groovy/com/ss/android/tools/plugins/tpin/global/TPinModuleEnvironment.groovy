package com.ss.android.tools.plugins.tpin.global

import org.gradle.api.Project


class TPinModuleEnvironment {

    private static Map<String, TPinModuleEnvironmentImpl> mImplMap = new HashMap<>()

    static void init(Project project) {
        getInstance(project)
    }

    static TPinModuleEnvironmentImpl getInstance(Project project) {
        def impl = mImplMap.get(project)

        if (impl == null) {
            impl = new TPinModuleEnvironmentImpl(project)
            mImplMap.put(project, impl)
        }

        return impl
    }
}