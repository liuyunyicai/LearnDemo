package com.ss.android.tools.plugins.core

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestedExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class TPinModuleCodeCheckPlugin implements Plugin<Project> {

    Project project
    Map<String, List<String>> pinModuleReferenceMap

    void apply(Project project) {
        this.project = project

        project.afterEvaluate {
            pinModuleReferenceMap = TPinModuleTestPlugin.pinModuleReferenceMap

            def taskNamePrefix
            TestedExtension extension = (TestedExtension) project.extensions.getByName("android")
            if (extension instanceof LibraryExtension) {
                taskNamePrefix = 'package'
            } else {
                taskNamePrefix = 'merge'
            }
            extension.buildTypes.each {
                def buildType = it.name
                if (extension.productFlavors.size() == 0) {
                    check(taskNamePrefix, buildType, null)
                } else {
                    extension.productFlavors.each {
                        check(taskNamePrefix, buildType, it.name)
                    }
                }
            }
        }
    }

    def check(taskPrefix, buildType, productFlavor) {
        TPinModuleCodeCheck pinModuleCodeCheck

        def buildTypeFirstUp = upperCase(buildType)
        def productFlavorFirstUp = productFlavor != null ? upperCase(productFlavor) : ""

        def mergeResourcesTaskName = taskPrefix + productFlavorFirstUp + buildTypeFirstUp + "Resources"
        def packageResourcesTask = project.tasks.findByName(mergeResourcesTaskName)
        if (packageResourcesTask != null) {
            pinModuleCodeCheck = new TPinModuleCodeCheck(project, pinModuleReferenceMap)
            packageResourcesTask.doLast {
                pinModuleCodeCheck.checkResources(mergeResourcesTaskName)
            }
        }

        def compileJavaTaskName = "compile${productFlavorFirstUp}${buildTypeFirstUp}JavaWithJavac"
        def compileJavaTask = project.tasks.findByName(compileJavaTaskName)
        if (compileJavaTask != null) {
            compileJavaTask.doLast {
                if (pinModuleCodeCheck == null) {
                    pinModuleCodeCheck = new TPinModuleCodeCheck(project, pinModuleReferenceMap)
                }
                def productFlavorBuildType = productFlavor != null ? (productFlavor + File.separator + buildType) : buildType
                pinModuleCodeCheck.checkClasses(productFlavorBuildType, mergeResourcesTaskName)
            }
        }
    }

    def upperCase(String str) {
        char[] ch = str.toCharArray()
        if (ch.size() == 0) return str

        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] -= 32
        }
        return String.valueOf(ch)
    }

}
