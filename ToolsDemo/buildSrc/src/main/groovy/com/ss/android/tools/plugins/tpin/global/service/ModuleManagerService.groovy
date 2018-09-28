package com.ss.android.tools.plugins.tpin.global.service

import com.ss.android.tools.plugins.tpin.global.service.container.IModuleContainer
import com.ss.android.tools.plugins.tpin.global.service.container.ModuleContainer
import com.ss.android.tools.plugins.tpin.global.service.context.IExecutorContext
import com.ss.android.tools.plugins.tpin.model.GlobalEnviModel
import com.ss.android.tools.plugins.tpin.model.TPinModuleModel
import com.ss.android.tools.plugins.tpin.utils.TPinFlavorHelper
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import com.sun.istack.Nullable
import org.gradle.api.Project
import org.gradle.internal.impldep.org.apache.http.util.TextUtils

/**
 * 对当前添加的module进行管理
 **/
class ModuleManagerService implements IExecutorContext{
    private static final String MAIN_FLAVOR_NAME = "main"

    Project mProject

    String mCurFlavor = null

    /**
     * 当前所apply的module
     **/
    TPinModuleModel mCurrentApplyModule

    GlobalEnviModel mGlobalEnviModel
    /**
     * mainModule会一直放在第一个
     **/
    IModuleContainer<TPinModuleModel> mPinModuleContainer = new ModuleContainer<>()

    /**
     * 不同flavor包含不同的moduleSet； 如果flavor未指定mainModule，则使用main flavor的mainModule
     **/
    Map<String, IModuleContainer> mFlavorModulesContainer = new HashMap<>()

    // 如果为第一次添加，则应清空之间的设置
    boolean mOriginSourceSetCleared

    ModuleManagerService(Project project) {
        mProject = project
        mGlobalEnviModel = new GlobalEnviModel()
    }

    /**
     * 添加module
     **/
    void includeModule(String name, String flavor = "main", String rootDir = null, boolean isMain = false, String codeRootDir = null) {
        TPinUtils.logInfo("includeModule", name, flavor, rootDir, isMain)

        // 如果为第一次添加，则应清空之间的设置
        if (!mOriginSourceSetCleared) {
            clearModules()
            mOriginSourceSetCleared = true
        }

        TPinModuleModel module = mPinModuleContainer.getByName(name)

        if (null != module) {
            // 重复添加需要判断是否两次参数不同
            assertModuleSame(name, rootDir, isMain, module)
        } else {
            // 未添加过，则需要先创建，然后添加
            module = new TPinModuleModel.Builder()
                    .project(mProject)
                    .name(name)
                    .rootDir(rootDir)
                    .codeRootDir(codeRootDir)
                    .mainModule(isMain)
                    .build()
            mPinModuleContainer.addOrThrow(module, isMain)
        }

        includeModuleWithFlavor(module, flavor, isMain)
    }

    /**
     * 将module添加到对应的flavor Container中
     **/
    private void includeModuleWithFlavor(TPinModuleModel module, String flavor, boolean isMain) {
        IModuleContainer moduleContainer = mFlavorModulesContainer.get(flavor)
        if (null == moduleContainer) {
            moduleContainer = new ModuleContainer()
            mFlavorModulesContainer.put(flavor, moduleContainer)
        }
        moduleContainer.add(module, isMain)
    }



    /**
     *TODO: 删除相应module
     **/

    /**
     * 清楚所有module
     **/
    void clearModules() {
        TPinUtils.logInfo("clearModules")
        mPinModuleContainer.clear()
        mFlavorModulesContainer.clear()
    }

    /**
     * 判断是否已经设置了mainModule；不能重复设置
     **/
    void assetMainModuleNotSetted() {
        if (isMainModuleSetted()) {
            TPinUtils.throwException("Main Pin Module Cannot set twice")
        }
    }

    /**
     * 重复添加Module且参数不同，则报异常
     **/
    void assertModuleSame(String name, String rootDir = null, boolean isMain = false, TPinModuleModel oldModule) {
        boolean isSame = true

        if (!oldModule.mName.equals(name)) {
            isSame = false
        } else if (null != rootDir && !oldModule.mRootDir.equals(rootDir)) {
            isSame = false
        } else if (oldModule.isMainModule != isMain) {
            isSame = false
        }

        if (!isSame) {
            TPinUtils.throwException("$name Module set twice, but config different")
        }
    }

    boolean isMainModuleSetted() {
        return mPinModuleContainer.isMainSetted()
    }

    @Override
    Collection<TPinModuleModel> getPinModules() {
        return mPinModuleContainer.getAlls()
    }

    @Override
    void setCurrentApplyModel(TPinModuleModel apply) {
        mCurrentApplyModule = apply
    }

    @Override
    TPinModuleModel getCurrentApplyModule() {
        return mCurrentApplyModule
    }

    @Override
    Project getProject() {
        return mProject
    }

    @Override
    String getMainFlavor() {
        return MAIN_FLAVOR_NAME
    }

    @Override
    String getCurFlavor() {
        if (TPinUtils.isEmpty(mCurFlavor)) {
            def flavor = TPinFlavorHelper.getCurrentFlavor(mProject)
            mCurFlavor = TPinUtils.isEmpty(flavor) ? MAIN_FLAVOR_NAME : flavor
        }
        return mCurFlavor
    }

    @Override
    Collection<String> getAllFlavors() {
        return mFlavorModulesContainer.keySet()
    }

    @Override
    GlobalEnviModel getGlobalEnviModel() {
        return mGlobalEnviModel
    }

    @Override
    TPinModuleModel getFlavorMainModule(String flavor) {
        def flavorModule = getSingleFlavorMainModule(flavor)
        def mainModule = getSingleFlavorMainModule(MAIN_FLAVOR_NAME)

        // flavor优先级高于main
        if (null != flavorModule) {
            return flavorModule
        }
        return mainModule
    }

    @Override
    Collection<TPinModuleModel> getFlavorPinModules(String flavor) {
        def flavorModules = getSingleFlavorPinModules(flavor)
        def mainModules = getSingleFlavorPinModules(MAIN_FLAVOR_NAME)

        TPinUtils.logInfo("getFlavorPinModules", flavorModules, mainModules)

        return TPinUtils.mergeCollections(flavorModules, mainModules)
    }

    @Override
    TPinModuleModel getCurFlavorMainModule() {
        return getFlavorMainModule(curFlavor)
    }

    @Override
    Collection<TPinModuleModel> getCurFlavorPinModules() {
        return getFlavorPinModules(curFlavor)
    }

    @Override
    TPinModuleModel getSingleFlavorMainModule(String flavor) {
        def container = mFlavorModulesContainer.get(flavor)
        return null == container ? null : container.getMain()
    }

    @Override
    Collection<TPinModuleModel> getSingleFlavorPinModules(String flavor) {
        def container = mFlavorModulesContainer.get(flavor)
        return null == container ? null : container.getAlls()
    }

    @Override
    @Nullable
    TPinModuleModel getMainModule() {
        return mPinModuleContainer.getMain()
    }
}