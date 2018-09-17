package com.ss.android.tools.plugins.tpin.plugins

import com.ss.android.tools.plugins.tpin.global.TPinModuleEnvironment
import com.ss.android.tools.plugins.tpin.utils.TPinFlavorHelper
import com.ss.android.tools.plugins.tpin.utils.TPinGradleUtils
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import org.gradle.api.Plugin
import org.gradle.api.Project

class TPinModulePlugin implements Plugin<Project> {
    Project mProject

    @Override
    void apply(Project project) {
        mProject = project
        TPinModuleEnvironment.init(project)
    }
}