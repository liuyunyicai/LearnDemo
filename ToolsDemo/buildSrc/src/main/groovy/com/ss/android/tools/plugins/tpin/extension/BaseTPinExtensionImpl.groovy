package com.ss.android.tools.plugins.tpin.extension

import org.gradle.api.Project

abstract class BaseTPinExtensionImpl implements ITPinExtension {
    Project mProject

    BaseTPinExtensionImpl(Project project) {
        mProject = project
    }
}