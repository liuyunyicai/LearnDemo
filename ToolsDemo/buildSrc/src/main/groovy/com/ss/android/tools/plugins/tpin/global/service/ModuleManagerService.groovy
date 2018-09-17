package com.ss.android.tools.plugins.tpin.global.service

import com.ss.android.tools.plugins.tpin.global.executors.context.IExecutorContext
import com.ss.android.tools.plugins.tpin.model.GlobalEnviModel
import com.ss.android.tools.plugins.tpin.model.TPinModuleModel
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import com.sun.istack.Nullable
import org.gradle.api.Project

/**
 * 对当前添加的module进行管理
 **/
class ModuleManagerService implements IExecutorContext{
    Project mProject

    /**
     * 当前所apply的module
     **/
    TPinModuleModel mCurrentApplyModule

    GlobalEnviModel mGlobalEnviModel
    /**
     * mainModule会一直放在第一个
     **/
    PSet<TPinModuleModel> mPinModulesSet = new PSet<>()

    // 如果为第一次添加，则应清空之间的设置
    boolean mOriginSourceSetCleared

    ModuleManagerService(Project project) {
        mProject = project
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
                    .codeRootDir(codeRootDir)
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

    @Override
    Iterator<TPinModuleModel> getPinModules() {
        return mPinModulesSet.iterator()
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
    GlobalEnviModel getGlobalEnviModel() {
        return mGlobalEnviModel
    }

    @Override
    @Nullable
    TPinModuleModel getMainModule() {
        def first = mPinModulesSet.first()

        if (first.isMainModule) {
            return first
        }

        return null
    }

    static class PSet<TPinModuleModel> extends TreeSet<TPinModuleModel> {
        TPinModuleModel find = null
        TPinModuleModel get(String name) {
            this.each {
                if (it.mName.equals(name)) {
                    find = it
                    return true
                }
            }
            return find
        }
    }
}