package com.ss.android.tools.plugins.core

class TPinUtils {

    static void logInfo(String... infos) {
        String info = ""
        infos.each {
            info += " ${it}"
        }
        println("=====================" + info)
    }
}