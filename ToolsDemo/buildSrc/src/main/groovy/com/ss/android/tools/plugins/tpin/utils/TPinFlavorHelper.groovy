package com.ss.android.tools.plugins.tpin.utils

import com.ss.android.tools.plugins.tpin.extension.factory.ExtensionFactory
import com.ss.android.tools.plugins.tpin.global.executors.factory.ExecutorFactory
import org.gradle.api.Project
import org.gradle.api.invocation.Gradle

import java.util.regex.Matcher
import java.util.regex.Pattern

class TPinFlavorHelper {
    /**
     * 获取当前Flavor
     **/
    static def getCurrentFlavor(Project project) {
        Gradle gradle = project.getGradle()
        String tskReqStr = gradle.getStartParameter().getTaskRequests().toString()

        Pattern pattern

        if (tskReqStr.contains("assemble"))
            pattern = Pattern.compile("assemble(\\w+)(Release|Debug)")
        else
            pattern = Pattern.compile("generate(\\w+)(Release|Debug)")

        Matcher matcher = pattern.matcher(tskReqStr)

        String flavor = null
        if (matcher.find()) {
            flavor =  matcher.group(1).toLowerCase()
        }

        TPinUtils.logInfo ("getCurrentFlavor", flavor)
        return flavor
    }

    /**
     * 获取当前applicationId
     **/
    static def getCurrentApplicationId(Project project) {
        def currFlavor = getCurrentFlavor(project)

        def outStr = ''
        getFlavors(project).all { flavor ->
            if (flavor.name == currFlavor) {
                outStr = flavor.applicationId
                return true
            }
        }
        return outStr
    }

    /**
     * 获取所有Flavor
     **/
    static def getFlavors(Project project) {
        return ExtensionFactory.getAndroidExtension(project).productFlavors
    }

    static def getBuildTypes(Project project) {
        return ExtensionFactory.getAndroidExtension(project).buildTypes
    }
}