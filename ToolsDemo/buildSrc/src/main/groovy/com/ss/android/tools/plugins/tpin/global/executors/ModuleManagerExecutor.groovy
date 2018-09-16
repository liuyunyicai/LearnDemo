package com.ss.android.tools.plugins.tpin.global.executors

import com.ss.android.tools.plugins.tpin.global.base.BaseExecutor
import com.ss.android.tools.plugins.tpin.module.GlobalEnviModel
import com.ss.android.tools.plugins.tpin.module.TPinModuleModel
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import org.gradle.api.Project

/**
 * 对当前添加的module进行管理
 **/
class ModuleManagerExecutor extends BaseExecutor{

    GlobalEnviModel mGlobalEnviModel
    /**
     * mainModule会一直放在第一个
     **/
    PSet<TPinModuleModel> mPinModulesSet = new PSet<>()

    // 如果为第一次添加，则应清空之间的设置
    boolean mOriginSourceSetCleared

    ModuleManagerExecutor(Project project) {
        super(project)
    }

    /**
     * 添加module
     **/
    void includeModule(String name, String flavor = "main", String rootDir = null, boolean isMain = false) {
        TPinUtils.logInfo("includeModule", name, flavor, rootDir, isMain)

        // 如果为第一次添加，则应清空之间的设置
        if (!mOriginSourceSetCleared) {
            clearModules()
            mOriginSourceSetCleared = true
        }

        TPinModuleModel module = mPinModulesSet.get(name)

        if (null != module) {
            // 重复添加需要判断是否两次参数不同
            assertModuleSame(name, rootDir, isMain, module)
            module.addFlavor(flavor)
        } else {
            // 未添加过，则需要先创建，然后添加
            module = new TPinModuleModel.Builder()
                    .project(mProject)
                    .name(name)
                    .flavor(flavor)
                    .rootDir(rootDir)
                    .mainModule(isMain)
                    .build()

            if (module.isMainModule) {
                assetMainModuleNotSetted()

                // 创建GlobalEnvModle
                mGlobalEnviModel = new GlobalEnviModel()

            }
            mPinModulesSet.add(module)
        }
    }



    /**
     *TODO: 删除相应module
     **/

    /**
     * 清楚所有module
     **/
    void clearModules() {
        TPinUtils.logInfo("clearModules")
        mPinModulesSet.clear()
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

    /**
     * 由于已经进行了排序，因此检查第一个即可
     **/
    boolean isMainModuleSetted() {
        def first = mPinModulesSet.first()

        if (first.isMainModule) {
            return true
        }
        return false
    }

    Iterator<TPinModuleModel> getPinModules() {
        return mPinModulesSet.iterator()
    }

    static class PSet<TPinModuleModel> extends TreeSet<TPinModuleModel> {
        TPinModuleModel get(String name) {
            this.each {
                if (it.mName.equals(name)) {
                    return it
                }
            }
            return null
        }
    }
}