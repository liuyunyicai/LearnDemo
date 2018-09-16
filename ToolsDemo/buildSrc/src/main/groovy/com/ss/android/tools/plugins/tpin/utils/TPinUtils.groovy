package com.ss.android.tools.plugins.tpin.utils

import com.ss.android.tools.plugins.core.AndroidManifest
import org.gradle.api.GradleException
import org.gradle.api.Project

class TPinUtils {

    /**
     * 创建File
     **/
    static File createFile(Project project, String rootDir, String relativePath) {
        return new File(project.projectDir.absolutePath + rootDir, relativePath)
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
}