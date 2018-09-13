package com.ss.android.tools.plugins.core

import org.gradle.api.GradleException
import org.gradle.api.Project

public class DefaultTPinModuleExtension implements TPinModuleExtension {

    public final static String MODULE_DIR = "/src/main/module/"
    public final static String MODULE_MAIN = "/src/"

    Project project

    // main Module； 默认方式以及mainModule两种指定方式
    TPinModule mainPinModule
    // 存储所有include进来的modules
    List<TPinModule> includeTpinModules
    OnPinModuleListener onPinModuleListener;

    DefaultTPinModuleExtension(Project project) {
        this.project = project
        this.includeTpinModules = new ArrayList<>()
    }

    @Override
    void include(String... pinModulePaths) {
        int pinModulePathsLen = pinModulePaths.size()
        for (int i = 0; i < pinModulePathsLen; i++) {
            TPinModule pinModule = buildTPinModule(pinModulePaths[i])
            if (pinModule == null) {
                throw new GradleException("can't find specified tpin-module '${pinModulePaths[i]}'.")
            }
            addTPinModule(pinModule)
            if(onPinModuleListener != null) {
                onPinModuleListener.addPinModule(pinModule, false)
            }
        }
    }

    @Override
    void mainPinModule(String pinModulePath) {
        mainPinModule = buildTPinModule(pinModulePath)
        if (mainPinModule == null) {
            throw new GradleException("can't find specified pin-module '${pinModulePath}'.")
        }
        if(onPinModuleListener != null) {
            onPinModuleListener.addPinModule(mainPinModule, true)
        }
    }

    /**
     * 生成pinModule的基本信息
     **/
    TPinModule buildTPinModule(String pinModulePath) {
        boolean isMainModule = pinModulePath.equals(":main") || pinModulePath.equals("main")
        String[] pathElements = removeTrailingColon(pinModulePath).split(":")
        int pathElementsLen = pathElements.size()
        File parentTPinModuleDir = project.projectDir

        // 由于可能有多层，所以这里需要进行转换
        String relativePath = isMainModule ? MODULE_MAIN : MODULE_DIR
        for (int j = 0; j < pathElementsLen; j++) {
            relativePath += pathElements[j] + "/"
        }
        parentTPinModuleDir = new File(parentTPinModuleDir, relativePath)
        File pinModuleDir = parentTPinModuleDir.canonicalFile
        String tPinModuleName = pinModuleDir.absolutePath.replace(project.projectDir.absolutePath, "")
        if (File.separator == "\\") {
            tPinModuleName = tPinModuleName.replaceAll("\\\\", ":")
        } else {
            tPinModuleName = tPinModuleName.replaceAll("/", ":")
        }

        TPinUtils.logInfo("buildTPinModule", pinModuleDir.absolutePath, pinModulePath, "" + isMainModule)

        if (!pinModuleDir.exists()) {
            return null
        }
        // TODO: Optimize
        String prefix = isMainModule ? "" : "/src/main"

        TPinModule pinModule = new TPinModule()
        pinModule.name = pinModulePath
        pinModule.pinModuleDir = pinModuleDir
        String absolutePath = pinModuleDir.absolutePath
        pinModule.mBuildGradleDir = isMainModule ? (project.projectDir.path + "/src/main") : (absolutePath + "/src")
        pinModule.mJavaSrcDirs.add(absolutePath + "${prefix}/java")
        pinModule.mResSrcDirs.add(absolutePath + "${prefix}/res")
        pinModule.mJNISrcDirs.add(absolutePath + "${prefix}/jni")
        pinModule.mJNILibsSrcDirs.add(absolutePath + "${prefix}/jniLibs")
        pinModule.mAIDLSrcDirs.add(absolutePath + "${prefix}/aidl")
        pinModule.mAssetsDirs.add(absolutePath + "${prefix}/assets")
        pinModule.mShadersSrcDirs.add(absolutePath + "${prefix}/shaders")
        pinModule.mResourcesSrcDirs.add(absolutePath + "${prefix}/resources")
        pinModule.mRendetScriptSrcDirs.add(absolutePath + "${prefix}/rs")
        return pinModule
    }

    private void addTPinModule(TPinModule pinModule) {
        for (int i = 0; i < includeTpinModules.size(); i++) {
            if (includeTpinModules.get(i).name.equals(pinModule.name)) {
                return
            }
        }
        includeTpinModules.add(pinModule)
    }

    private String removeTrailingColon(String pinModulePath) {
        return pinModulePath.startsWith(":") ? pinModulePath.substring(1) : pinModulePath
    }

    interface OnPinModuleListener {
        void addPinModule(TPinModule pinModule, boolean mainPinModule)
    }
}
