package com.ss.android.tools.plugins.tpin.global.executors

import com.ss.android.tools.plugins.tpin.global.executors.base.BaseExecutor
import com.ss.android.tools.plugins.tpin.global.service.context.IExecutorContext
import org.gradle.api.Project

abstract class AbsSetSourceSetExecutor extends BaseExecutor {

    /**
     * 如果当前flavor获取不到，则返回所有的flavor
     *
     * 如果当前task指定了flavor，只用执行当前flavor即可
     **/
    AbsSetSourceSetExecutor(Project project) {
        super(project)
    }

    Collection<String> getExcuteFlavors(IExecutorContext context) {
        if (context.curFlavor.equals(context.mainFlavor)) {
            return context.getAllFlavors()
        }

        List<String> modules = new ArrayList<>()
        modules.add(context.curFlavor)
        modules.add(context.mainFlavor)

        return modules
    }
}