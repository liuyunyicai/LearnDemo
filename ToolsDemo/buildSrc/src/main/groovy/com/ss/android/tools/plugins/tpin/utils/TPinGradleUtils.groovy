package com.ss.android.tools.plugins.tpin.utils

import com.ss.android.tools.plugins.core.AndroidManifest
import org.gradle.api.Project

class TPinGradleUtils {


    static def getPackageName = { File androidManifestFile ->
        AndroidManifest androidManifest = new AndroidManifest()
        androidManifest.load(androidManifestFile)
        androidManifest.packageName
    }

    /**
     * 创建File
     **/
    static File createFile(Project project, String rootDir, String relativePath) {
        return createFile(project, "$rootDir/$relativePath")
    }

    static File createFile(Project project, String path) {
        return new File(getAbsolutePath(project, path))
    }

    static def getAbsolutePath(Project project, String path) {
        if (path.charAt(0) != '/') {
            path = "/$path"
        }
        "$project.projectDir.absolutePath$path"
    }
}