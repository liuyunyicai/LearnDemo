package com.ss.android.tools.plugins.tpin.utils

import com.android.utils.ILogger
import com.ss.android.tools.plugins.core.AndroidManifest
import com.ss.android.tools.plugins.tpin.global.TPinModuleEnvironment
import org.gradle.api.GradleException
import org.gradle.api.Project

class TPinUtils {


    static def logInfo1 = { String message, Collection<Object> its ->
        if (!TPinModuleEnvironment.sDebuggable) {
            return
        }
        String info = message
        its.each {
            info += " ${it.toString()}"
        }
        println("=====================" + info)
    }


    static def logInfo = { Object... infos ->
        if (!TPinModuleEnvironment.sDebuggable) {
            return
        }

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

    /**
     * 无序，返回HashSet
     **/
    static <T> Collection<T> mergeCollections(Collection<T> c1, Collection<T> c2) {
        TPinUtils.logInfo("mergeCollections", c1, c2)

        if (c1 == c2) {
            return c1
        }

        Set<T> set = new HashSet<>()

        if (null != c1) {
            set.addAll(c1)
        }
        if (null != c2) {
            set.addAll(c2)
        }

        TPinUtils.logInfo("mergeCollections result ", set)
        return set
    }

    /**
     * 由于源数据中的集合不想被改变，因此添加到一个新的链表中可供修改
     **/
    static <T> List<T> getMutableNewList(Collection<T> origin) {
        List<T> newList = new ArrayList<>()
        newList.addAll(origin)
        return newList
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