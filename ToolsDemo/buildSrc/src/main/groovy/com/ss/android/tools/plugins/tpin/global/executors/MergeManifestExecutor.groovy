package com.ss.android.tools.plugins.tpin.global.executors

import com.android.build.gradle.BaseExtension
import com.android.manifmerger.ManifestMerger2
import com.android.manifmerger.MergingReport
import com.android.manifmerger.XmlDocument
import com.ss.android.tools.plugins.tpin.global.base.BaseExecutor
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import org.gradle.api.GradleException
import org.gradle.api.Project

class MergeManifestExecutor extends BaseExecutor {

    MergeManifestExecutor(Project project) {
        super(project)
    }

    void execute() {

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
}