package com.ss.android.tools.plugins

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.api.AndroidSourceDirectorySet
import com.android.manifmerger.ManifestMerger2
import com.android.manifmerger.MergingReport
import com.android.manifmerger.XmlDocument
import com.android.utils.ILogger
import com.ss.android.tools.plugins.core.*
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

class TPinModuleTestPlugin implements Plugin<Project> {

    Project project
    DefaultTPinModuleExtension pinModuleExtension

    TPinModule currentPinModule

    public static Map<String, List<String>> pinModuleReferenceMap

    boolean originSourceSetCleared

    public final static String RPath = "/build/generated/source/r/"
    public final static String mainManifestPath = "/AndroidManifest.xml"
    public final static String moduleManifestPath = "src/main/AndroidManifest.xml"

    def logger = new ILogger() {
        @Override
        void error(Throwable t, String msgFormat, Object... args) {
            println(msgFormat)
        }

        @Override
        void warning(String msgFormat, Object... args) {

        }

        @Override
        void info(String msgFormat, Object... args) {

        }

        @Override
        void verbose(String msgFormat, Object... args) {

        }
    }

    void apply(Project project) {
        this.project = project


        // Create pinModule DSL
        pinModuleExtension = project.extensions.create(TPinModuleExtension, "tpinModules", DefaultTPinModuleExtension, project)
        checkMainPinModule()

        // 使用tpinModules DSL时会触发该回掉
        pinModuleExtension.onPinModuleListener = new DefaultTPinModuleExtension.OnPinModuleListener() {
            @Override
            void addPinModule(TPinModule pinModule, boolean mainPinModule) {
                // originSourceSetCleared 用来表示是否未第一次添加，首次添加为false
                if (!originSourceSetCleared) {
                    clearPinModuleWithAllDimens()
                    if (!mainPinModule) {
                        includePinModuleWithAllDimens(pinModuleExtension.mainPinModule)
                    }
                    originSourceSetCleared = true
                }

                // 通过mainPinModule来指定main module
                if (mainPinModule) {
                    clearPinModuleWithAllDimens()
                    includePinModuleWithAllDimens(pinModuleExtension.mainPinModule)
                    pinModuleExtension.includeTpinModules.each {
                        if (it.name.equals(pinModuleExtension.mainPinModule.name))
                            return
                        includePinModuleWithAllDimens(it)
                    }
                } else {
                    includePinModuleWithAllDimens(pinModule)
                }
            }
        }

        project.dependencies.metaClass.pinModule { path ->
            pinModuleDependencyHandler(path)
            return []
        }

        project.afterEvaluate {
            TPinUtils.logInfo("afterEvaluate")

            pinModuleReferenceMap = new HashMap<>()

            if (!originSourceSetCleared) {
                clearPinModuleWithAllDimens()
                includePinModuleWithAllDimens(pinModuleExtension.mainPinModule)
                originSourceSetCleared = true
            }

            // ===== 执行所有module的build.gradle
            applyPinModuleBuild(pinModuleExtension.mainPinModule)
            List<TPinModule> includeMicroModules = pinModuleExtension.includeTpinModules.clone()
            includeMicroModules.each {
                if (it.name.equals(pinModuleExtension.mainPinModule.name))
                    return
                applyPinModuleBuild(it)
            }

            handlePinMicroModule()
            // 执行Build时的处理
            project.tasks.preBuild.doFirst {

                TPinUtils.logInfo("project.tasks.preBuild.doFirst")

                pinModuleReferenceMap = new HashMap<>()
                setPinModuleDir()
                handlePinMicroModule()
                generateR()
            }
        }
    }

    /**
     * 整体设置pinModule sourceSet
     **/
    def setPinModuleDir() {
        clearPinSourceSet("main")
        // include
        includePinModuleWithAllDimens(pinModuleExtension.mainPinModule)

        // TODO: To Optimize
        // 这么做的目的是为了避免includeTpinModules中存在mainModule的情况；比较丑陋
        pinModuleExtension.includeTpinModules.each {
            if (it.name.equals(pinModuleExtension.mainPinModule.name))
                return
            includePinModuleWithAllDimens(it)
        }
    }

    def handlePinMicroModule() {
        // check
        checkPinModuleReference(pinModuleExtension.mainPinModule)
        pinModuleExtension.includeTpinModules.each {
            if (it.name.equals(pinModuleExtension.mainPinModule.name))
                return
            checkPinModuleReference(it)
        }

        mergeMainAndroidManifest()
    }

    /**
     * Check is there a main module
     **/
    def checkMainPinModule() {
        if (pinModuleExtension.mainPinModule == null) {
            pinModuleExtension.mainPinModule(":main")
//            pinModuleExtension.mainPinModule = new TPinModule()
//            def name = ":main"
//            def pinModuleDir = new File(project.projectDir, MAIN_MODULE_DIR)
//            if (!pinModuleDir.exists()) {
//                throw new GradleException("can't find specified micro-module [${project.projectDir}] under path [${pinModuleDir.absolutePath}].")
//            }
//            pinModuleExtension.mainPinModule.name = name
//            pinModuleExtension.mainPinModule.pinModuleDir = pinModuleDir
        }
    }

    /**
     * 将pin结构中AndroidManifest merge到 build/tpinModule/main目录下面，并通过manifest.srcFile进行制定
     **/
    def mergeMainAndroidManifest() {
        File mainManifestFile = new File(pinModuleExtension.mainPinModule.pinModuleDir, mainManifestPath)
        ManifestMerger2.MergeType mergeType = ManifestMerger2.MergeType.APPLICATION
        XmlDocument.Type documentType = XmlDocument.Type.MAIN
        ManifestMerger2.Invoker invoker = new ManifestMerger2.Invoker(mainManifestFile, logger, mergeType, documentType)
        invoker.withFeatures(ManifestMerger2.Invoker.Feature.NO_PLACEHOLDER_REPLACEMENT)
        pinModuleExtension.includeTpinModules.each {
            if (it.name.equals(pinModuleExtension.mainPinModule.name)) return
            def microManifestFile = new File(it.pinModuleDir, moduleManifestPath)
            if (microManifestFile.exists()) {
                invoker.addLibraryManifest(microManifestFile)
                TPinUtils.logInfo("addLibraryManifest " + it.pinModuleDir)
            }
        }
        def mergingReport = invoker.merge()
        if (!mergingReport.result.success) {
            mergingReport.log(logger)
            throw new GradleException(mergingReport.reportString)
        }
        def moduleAndroidManifest = mergingReport.getMergedDocument(MergingReport.MergedManifestKind.MERGED)
        moduleAndroidManifest = new String(moduleAndroidManifest.getBytes("UTF-8"))

        def saveDir = new File(project.projectDir, "build/tpinModule/main")
        saveDir.mkdirs()
        def AndroidManifestFile = new File(saveDir, "AndroidManifest.xml")
        AndroidManifestFile.createNewFile()
        AndroidManifestFile.write(moduleAndroidManifest)

        def extensionContainer = project.getExtensions()
        BaseExtension android = extensionContainer.getByName('android')
        android.sourceSets.main.manifest.srcFile saveDir.absolutePath + "/AndroidManifest.xml"
    }

    def includePinModuleWithAllDimens(TPinModule pinModule) {
        includePinModuleSourceSet(pinModule, "main")
//        includePinModuleSourceSet(pinModule, "androidTest")
//        includePinModuleSourceSet(pinModule, "test")
    }

    def clearPinModuleWithAllDimens() {
        clearPinSourceSet("main")
//        clearPinSourceSet("androidTest")
//        clearPinSourceSet("test")
    }

    /**
     * 设置pinModule的sourceSet
     **/
    def includePinModuleSourceSet(TPinModule pinModule, def type) {
        BaseExtension android = project.extensions.getByName('android')
        def obj = android.sourceSets.getByName("main")
        setSourcesSet(obj.java, pinModule.mJavaSrcDirs)
        setSourcesSet(obj.res, pinModule.mResSrcDirs)
        setSourcesSet(obj.jni, pinModule.mJNISrcDirs)
        setSourcesSet(obj.jniLibs, pinModule.mJNILibsSrcDirs)
        setSourcesSet(obj.aidl, pinModule.mAIDLSrcDirs)
        setSourcesSet(obj.assets, pinModule.mAssetsDirs)
        setSourcesSet(obj.shaders, pinModule.mShadersSrcDirs)
        setSourcesSet(obj.resources, pinModule.mResourcesSrcDirs)
        setSourcesSet(obj.renderscript, pinModule.mRendetScriptSrcDirs)
    }

    def setSourcesSet(AndroidSourceDirectorySet set, List<String> dirs) {
        dirs.each {
            set.srcDir(it)
        }
    }

    /**
     * 清理之前pinModule设置的sourceSet
     **/
    def clearPinSourceSet(def type) {
        TPinUtils.logInfo("clearPinSourceSet")
//        def srcDirs = []
//        BaseExtension android = project.extensions.getByName('android')
//        def obj = android.sourceSets.getByName(type)
//        obj.java.srcDirs = srcDirs
//        obj.res.srcDirs = srcDirs
//        obj.jni.srcDirs = srcDirs
//        obj.jniLibs.srcDirs = srcDirs
//        obj.aidl.srcDirs = srcDirs
//        obj.assets.srcDirs = srcDirs
//        obj.shaders.srcDirs = srcDirs
//        obj.resources.srcDirs = srcDirs
//        obj.renderscript.srcDirs = srcDirs
    }

    def checkPinModuleReference(TPinModule pinModule) {
        List<String> referenceList = pinModuleReferenceMap.get(pinModule.name)
        if (referenceList == null)
            return

        for (String path : referenceList) {
            isReferenceMicroModuleInclude(pinModule, path)
        }
    }

    def isReferenceMicroModuleInclude(TPinModule tPinModule, String path) {
        TPinUtils.logInfo("isReferenceMicroModuleInclude", tPinModule.name, path)

        TPinModule pinModule = pinModuleExtension.buildTPinModule(path)
        if (pinModule == null) {
            throw new GradleException("can't find specified pinModule '${path}', which is referenced by pinModule${tPinModule.name}")
        }

        boolean include = false
        pinModuleExtension.includeTpinModules.each {
            if (it.name == pinModule.name) {
                include = true
            }
        }
        if (pinModuleExtension.mainPinModule.name == pinModule.name) {
            include = true
        }
        if (!include) {
            throw new GradleException("pinModule${pinModule.name} is referenced by pinModule${tPinModule.name}, but its not included.")
        }
    }

    /**
     * 生成R
     **/
    def generateR() {
        def microManifestFile = new File(pinModuleExtension.mainPinModule.pinModuleDir, mainManifestPath)
        def mainPackageName = getPackageName(microManifestFile)
        BaseExtension extension = (BaseExtension) project.extensions.getByName("android")
        extension.buildTypes.each {
            def buildType = it.name
            if (extension.productFlavors.size() == 0) {
                generateRByProductFlavorBuildType(mainPackageName, buildType, null)
            } else {
                extension.productFlavors.each {
                    generateRByProductFlavorBuildType(mainPackageName, buildType, it.name)
                }
            }
        }
    }

    def generateRByProductFlavorBuildType(mainPackageName, buildType, productFlavor) {
        def buildTypeFirstUp = upperCase(buildType)
        def productFlavorFirstUp = productFlavor != null ? upperCase(productFlavor) : ""
        def processResourcesTaskName = "process${productFlavorFirstUp}${buildTypeFirstUp}Resources"
        def processResourcesTask = project.tasks.findByName(processResourcesTaskName)
        if (processResourcesTask != null) {
            processResourcesTask.doLast {
                def productFlavorBuildType = productFlavor != null ? (productFlavor + "/" + buildType) : buildType
                def path = project.projectDir.absolutePath + RPath + productFlavorBuildType + "/" + mainPackageName.replace(".", "/") + "/R.java"
                def file = project.file(path)
                def newR = file.text.replace("public final class R", "public class R")
                file.write(newR)
                generatePinModuleResources(mainPackageName, productFlavorBuildType)
            }
        } else {
            def generateRFileTaskName = "generate${productFlavorFirstUp}${buildTypeFirstUp}RFile"
            def generateRFileTask = project.tasks.findByName(generateRFileTaskName)
            if (generateRFileTask != null) {
                generateRFileTask.doLast {
                    def productFlavorBuildType = productFlavor != null ? (productFlavor + "/" + buildType) : buildType
                    def path = project.projectDir.absolutePath + RPath + productFlavorBuildType + "/" + mainPackageName.replace(".", "/") + "/R.java"
                    def file = project.file(path)
                    def newR = file.text.replace("public final class R", "public class R")
                    file.write(newR)
                    generatePinModuleResources(mainPackageName, productFlavorBuildType)
                }
            }
        }
    }

    def upperCase(String str) {
        char[] ch = str.toCharArray()
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] -= 32
        }
        return String.valueOf(ch)
    }

    def getPackageName(File androidManifestFile) {
        AndroidManifest androidManifest = new AndroidManifest()
        androidManifest.load(androidManifestFile)
        return androidManifest.packageName
    }

    def generatePinModuleResources(packageName, productFlavorBuildType) {
        def packageNames = []
        pinModuleExtension.includeTpinModules.each {
            def pinManifestFile = new File(it.pinModuleDir, mainManifestPath)
            if (!pinManifestFile.exists()) {
                return
            }
            def pinModulePackageName = getPackageName(pinManifestFile)
            if (pinModulePackageName == null || packageNames.contains(pinModulePackageName)) return

            packageNames << pinModulePackageName
            def path = project.projectDir.absolutePath + RPath + productFlavorBuildType + "/" + pinModulePackageName.replace(".", "/")
            File file = project.file(path + "/R.java")
            if (project.file(path).exists()) return
            project.file(path).mkdirs()
            file.write("package " + pinModulePackageName + ";\n\n/** This class is generated by micro-module plugin, DO NOT MODIFY. */\npublic class R extends " + packageName + ".R {\n\n}")
            println "[micro-module] - microModule${it.name} generate " + pinModulePackageName + '.R.java'
        }
    }

    /**
     * 执行每一个module的build.gradle
     **/
    void applyPinModuleBuild(TPinModule pinModule) {
        def pinModuleBuild = new File(pinModule.mBuildGradleDir, "build.gradle")

        TPinUtils.logInfo("applyPinModuleBuild", pinModule.mBuildGradleDir)

        if (pinModuleBuild.exists()) {
            currentPinModule = pinModule
            project.apply from: pinModuleBuild.absolutePath
        }
    }

    def pinModuleDependencyHandler(String path) {
        TPinUtils.logInfo("pinModuleDependencyHandler", path)

        if (pinModuleExtension == null || currentPinModule == null) {
            return
        }
        TPinModule pinModule = pinModuleExtension.buildTPinModule(path)
        if (pinModule == null) {
            throw new GradleException("can't find specified pinModule '${path}', which is referenced by pinModle${currentPinModule.name}")
        }
        List<String> referenceList = pinModuleReferenceMap.get(currentPinModule.name)
        if (referenceList == null) {
            referenceList = new ArrayList<>()
            referenceList.add(pinModule.name)
            pinModuleReferenceMap.put(currentPinModule.name, referenceList)
        } else {
            if (!referenceList.contains(pinModule.name)) {
                referenceList.add(pinModule.name)
            }
        }
    }

}