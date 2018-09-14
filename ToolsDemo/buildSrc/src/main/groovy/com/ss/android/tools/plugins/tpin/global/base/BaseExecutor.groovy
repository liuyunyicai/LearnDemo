package com.ss.android.tools.plugins.tpin.global.base

import org.gradle.api.Project

abstract class BaseExecutor {

    Project mProject

    BaseExecutor(Project project) {
        mProject = project
    }
}