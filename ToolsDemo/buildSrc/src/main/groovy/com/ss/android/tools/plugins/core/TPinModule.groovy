package com.ss.android.tools.plugins.core

class TPinModule {
    String name
    File pinModuleDir
    String mBuildGradleDir

    // Can Use AndroidSourceSet
    List<String> mJavaSrcDirs = new ArrayList<>()
    List<String> mResSrcDirs = new ArrayList<>()
    List<String> mJNISrcDirs = new ArrayList<>()
    List<String> mJNILibsSrcDirs = new ArrayList<>()
    List<String> mAIDLSrcDirs = new ArrayList<>()
    List<String> mAssetsDirs = new ArrayList<>()
    List<String> mShadersSrcDirs = new ArrayList<>()
    List<String> mResourcesSrcDirs = new ArrayList<>()
    List<String> mRendetScriptSrcDirs = new ArrayList<>()


}