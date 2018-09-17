package com.ss.android.tools.plugins.tpin.global.executors.context

import com.ss.android.tools.plugins.tpin.model.GlobalEnviModel
import com.ss.android.tools.plugins.tpin.model.TPinModuleModel
import org.gradle.api.Project

interface IExecutorContext {
    Project getProject()

    GlobalEnviModel getGlobalEnviModel()

    TPinModuleModel getMainModule()

    Iterator<TPinModuleModel> getPinModules()

    void setCurrentApplyModel(TPinModuleModel apply)

    TPinModuleModel getCurrentApplyModule()
}