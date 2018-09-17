package com.ss.android.tools.plugins.tpin.utils

import com.android.utils.ILogger
import com.ss.android.tools.plugins.core.AndroidManifest
import com.ss.android.tools.plugins.tpin.global.TPinModuleEnvironment
import org.gradle.api.GradleException
import org.gradle.api.Project

class TPinUtils {


    static def logInfo1 = { String message, Collection<Object> its ->
        String info = message
        its.each {
            info += " ${it.toString()}"
        }
        println("=====================" + info)
    }


    static def logInfo = { Object... infos ->
        String info = ""
        infos.each {
            info += " ${it.toString()}"
        }
        println("=====================" + info)
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