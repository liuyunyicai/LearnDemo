package com.ss.android.tools.plugins.tpin.global.service.context

import com.ss.android.tools.plugins.tpin.model.GlobalEnviModel
import com.ss.android.tools.plugins.tpin.model.TPinModuleModel
import org.gradle.api.Project

interface IExecutorContext {
    Project getProject()

    String getMainFlavor()

    String getCurFlavor()

    Collection<String> getAllFlavors()

    GlobalEnviModel getGlobalEnviModel()

    /**注意这里会包含main flavor中数据*/
    TPinModuleModel getFlavorMainModule(String flavor)

    Collection<TPinModuleModel> getFlavorPinModules(String flavor)

    /** **/
    TPinModuleModel getCurFlavorMainModule()

    Collection<TPinModuleModel> getCurFlavorPinModules()


    TPinModuleModel getSingleFlavorMainModule(String flavor)

    Collection<TPinModuleModel> getSingleFlavorPinModules(String flavor)



    TPinModuleModel getMainModule()

    Collection<TPinModuleModel> getPinModules()



    void setCurrentApplyModel(TPinModuleModel apply)

    TPinModuleModel getCurrentApplyModule()
}