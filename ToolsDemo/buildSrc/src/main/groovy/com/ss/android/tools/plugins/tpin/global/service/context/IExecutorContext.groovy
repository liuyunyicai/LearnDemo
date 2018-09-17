package com.ss.android.tools.plugins.tpin.global.service.context

import com.ss.android.tools.plugins.tpin.model.GlobalEnviModel
import com.ss.android.tools.plugins.tpin.model.TPinModuleModel
import org.gradle.api.Project

interface IExecutorContext {
    Project getProject()

    GlobalEnviModel getGlobalEnviModel()

    TPinModuleModel getMainModule()

    Collection<TPinModuleModel> getPinModules()

    void setCurrentApplyModel(TPinModuleModel apply)

    TPinModuleModel getCurrentApplyModule()
}