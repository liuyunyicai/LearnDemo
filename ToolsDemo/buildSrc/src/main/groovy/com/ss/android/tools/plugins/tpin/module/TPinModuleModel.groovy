package com.ss.android.tools.plugins.tpin.module

import com.android.build.gradle.api.AndroidSourceSet
import com.android.build.gradle.internal.api.DefaultAndroidSourceSet
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import org.gradle.api.Project
import org.jetbrains.annotations.NotNull

/**
 * 每一个pin module对应的基本类型
 **/
class TPinModuleModel implements Comparable<TPinModuleModel> {
    // 是否为主module
    boolean isMainModule

    // Module name (格式可以设置为:common:module)
    String mName

    // 设置build.gradle所在路径
    String mRootDir

    // 配置module的sourceset等属性
    AndroidSourceSet mAndroidSourceSet

    /**
     * 包含该module的Flavor
     **/
    Set<String> mFlavors = new HashSet<>()

    private TPinModuleModel() {

    }

    private TPinModuleModel(ContentInfo info) {
        if (null == info.mRootDir) {
            // 生成默认rootDir
            info.mRootDir = getRootDir(info.mProject, info.mName, info.mRootDir, info.isMainModule)
        }
        TPinUtils.logInfo("getRootDir", info.mRootDir)

        isMainModule = info.isMainModule
        mName = info.mName
        mRootDir = info.mRootDir
        // TODO: publishPackage暂不知是什么意思，getPackageConfigurationName会用到；这里只是用AndroidSourceSet的srcDir
        mAndroidSourceSet = new DefaultTPinModuleAndroidSourceSet(info.mName, info.mProject, false, info.mRootDir)
        addFlavor(info.mFlavor)
        TPinUtils.logInfo("buildPinModule", mName, mAndroidSourceSet.java.srcDirs)
    }


    static class Builder {
        ContentInfo info = new ContentInfo()

        Builder project(Project project) {
            info.mProject = project
            return this
        }

        Builder mainModule(boolean isMainModule) {
            info.isMainModule = isMainModule
            return this
        }

        Builder name(String name) {
            info.mName = name
            return this
        }

        Builder rootDir(String rootDir) {
            info.mRootDir = rootDir
            return this
        }

        Builder flavor(String flavor) {
            info.mFlavor = flavor
            return this
        }

        TPinModuleModel build() {
            return new TPinModuleModel(info)
        }
    }

    static class ContentInfo {
        Project mProject
        boolean isMainModule
        String mName
        String mRootDir
        String mFlavor
    }

    @Override
    int compareTo(@NotNull TPinModuleModel tPinModuleModel) {
        if (isMainModule && !tPinModuleModel.isMainModule) {
            return -1
        }
        // name相同，即视为同一module
        if (this.mName == tPinModuleModel.mName) {
            return 0
        }
        return 1
    }


    static class DefaultTPinModuleAndroidSourceSet extends DefaultAndroidSourceSet{

        DefaultTPinModuleAndroidSourceSet(String name, Project project, boolean publishPackage, String rootDir) {
            super(name, project, publishPackage)
            setRoot(rootDir + "/src/main/")
        }
    }

    /**
     * 根据moduleName 生成相对路径, 默认src/module
     * main 则直接为根目录
     **/
    static String getRootDir(Project project, String name, String rootDir = null, boolean isMain = false) {
        if (!TPinUtils.isEmpty(rootDir)) {
            return rootDir
        }

        if (isMain) {
            return ""
        }
        return convertNameToPath(project, name)
    }

    /**
     * 添加包含该module的flavor
     **/
    void addFlavor(String flavor) {
        mFlavors.add(flavor)
    }


    private static String convertNameToPath(Project project, String name) {
        String[] pathElements = removeTrailingColon(name).split(":")
        int pathElementsLen = pathElements.size()
        String relativePath = "/src/"
        for (int j = 0; j < pathElementsLen; j++) {
            relativePath += pathElements[j] + "/"
        }
        return relativePath
    }

    private static String removeTrailingColon(String pinModulePath) {
        return pinModulePath.startsWith(":") ? pinModulePath.substring(1) : pinModulePath
    }
}


