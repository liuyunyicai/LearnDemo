package com.ss.android.tools.plugins.tpin.global

import com.ss.android.tools.plugins.tpin.global.base.BaseExecutor
import com.ss.android.tools.plugins.tpin.module.TPinModuleModel
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import org.gradle.api.Project

/**
 * 对当前添加的module进行管理
 **/
class ModuleManagerExecutor extends BaseExecutor{
    /**
     * mainModule会一直放在第一个
     **/
    Map<TPinModuleModel, String> mPinModulesMap = new TreeMap<>()

    // 如果为第一次添加，则应清空之间的设置
    boolean mOriginSourceSetCleared

    ModuleManagerExecutor(Project project) {
        super(project)
    }

    /**
     * 添加module
     **/
    void includeModule(String name, String rootDir = null, boolean isMain = false) {
        TPinUtils.logInfo("includeModule", name, rootDir, isMain)

        // 如果为第一次添加，则应清空之间的设置
        if (!mOriginSourceSetCleared) {
            clearModules()
            mOriginSourceSetCleared = true
        }

        includeModule(TPinModuleModel.buildPinModule(mProject, name, rootDir, isMain))
    }

    void includeModule(TPinModuleModel module) {
        TPinUtils.logInfo("includeModule", module.mName)

        if (module.isMainModule) {
            assetMainModuleNotSetted()
        }
        mPinModulesMap.put(module, module.mName)
    }

    /**
     *TODO: 删除相应module
     **/

    /**
     * 清楚所有module
     **/
    void clearModules() {
        TPinUtils.logInfo("clearModules")
        mPinModulesMap.clear()
    }

    /**
     * 判断是否已经设置了mainModule；不能重复设置
     **/
    void assetMainModuleNotSetted() {
        if (isMainModuleSetted()) {
            TPinUtils.throwException("Main Module Cannot set twice")
        }
    }

    /**
     * 由于已经进行了排序，因此检查第一个即可
     **/
    boolean isMainModuleSetted() {
        def first = mPinModulesMap.keySet().first()
        if (null != first && first.isMainModule) {
            return true
        }
        return false
    }

    Iterable<TPinModuleModel> getPinModules() {
        return mPinModulesMap.keySet()
    }
}