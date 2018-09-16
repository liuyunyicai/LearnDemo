package com.ss.android.tools.plugins.tpin.utils

import com.android.utils.ILogger
import com.ss.android.tools.plugins.core.AndroidManifest
import com.ss.android.tools.plugins.tpin.global.TPinModuleEnvironment
import org.gradle.api.GradleException
import org.gradle.api.Project

class TPinUtils {

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

    static def logInfo = { Object... infos ->
        String info = ""
        infos.each {
            info += " ${it.toString()}"
        }
        println("=====================" + info)
    }

    static def getPackageName = { File androidManifestFile ->
        AndroidManifest androidManifest = new AndroidManifest()
        androidManifest.load(androidManifestFile)
        androidManifest.packageName
    }

    static def removeTrailingColon = { String pinModulePath ->
        pinModulePath.startsWith(":") ? pinModulePath.substring(1) : pinModulePath
    }

    static def throwException(Object... infos) {
        String info = ""
        infos.each {
            info += " ${it.toString()}"
        }
        throw new GradleException(info)
    }

    static <T> List<T> variableParamsToList(T... objs) {
        List<T> list = new ArrayList<>()
        objs.each {
            list.add(it)
        }
        return list
    }

    static boolean isEmpty(CharSequence s) {
        if (null == s || s.equals("")) {
            return true
        }
        return false
    }

    static def logger = new ILogger() {
        @Override
        void error(Throwable t, String msgFormat, Object... args) {
        }

        @Override
        void warning(String msgFormat, Object... args) {

        }

        @Override
        void info(String msgFormat, Object... args) {

        }

        @Override
        void verbose(String msgFormat, Object... args) {

        }
    }
}