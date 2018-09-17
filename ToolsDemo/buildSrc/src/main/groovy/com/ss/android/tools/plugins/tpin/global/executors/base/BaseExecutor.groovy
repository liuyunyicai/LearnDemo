package com.ss.android.tools.plugins.tpin.global.executors.base

import com.ss.android.tools.plugins.tpin.global.service.context.IExecutorContext
import org.gradle.api.Project

abstract class BaseExecutor {

    Project mProject

    BaseExecutor(Project project) {
        mProject = project
    }

    void setProject(Project project) {
        mProject = project
    }

    abstract void execute(IExecutorContext context)
}