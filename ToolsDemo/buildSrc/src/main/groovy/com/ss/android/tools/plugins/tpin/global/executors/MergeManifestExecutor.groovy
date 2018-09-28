package com.ss.android.tools.plugins.tpin.global.executors

import com.android.build.gradle.BaseExtension
import com.android.manifmerger.ManifestMerger2
import com.android.manifmerger.MergingReport
import com.android.manifmerger.XmlDocument
import com.ss.android.tools.plugins.tpin.global.TPinModuleEnvironment
import com.ss.android.tools.plugins.tpin.global.executors.base.BaseExecutor
import com.ss.android.tools.plugins.tpin.global.service.context.IExecutorContext
import com.ss.android.tools.plugins.tpin.model.GlobalEnviModel
import com.ss.android.tools.plugins.tpin.model.TPinModuleModel
import com.ss.android.tools.plugins.tpin.utils.TPinGradleUtils
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import org.gradle.api.GradleException
import org.gradle.api.Project

class MergeManifestExecutor extends BaseExecutor {

    static String MANIFEST_NAME = "/AndroidManifest.xml"

    MergeManifestExecutor(Project project) {
        super(project)
    }

    @Override
    void execute(IExecutorContext context) {
        mergeMainAndroidManifest(context.globalEnviModel, context.curFlavorPinModules, context.curFlavorMainModule)
    }

    /**
     * 将pin结构中AndroidManifest merge到 build/tpinModule/main目录下面，并通过manifest.srcFile进行制定
     **/
    def mergeMainAndroidManifest(GlobalEnviModel globalEnviModel, Collection<TPinModuleModel> modules, TPinModuleModel mainModule) {
        String moduleAndroidManifest = doMerge(mainModule, modules)

        File saveDir = doSave(globalEnviModel, moduleAndroidManifest)

        setManifestSourceSet(saveDir.absolutePath + MANIFEST_NAME)
    }

    /**
     * save
     **/
    private File doSave(GlobalEnviModel globalEnviModel, String moduleAndroidManifest) {
        def saveDir = TPinGradleUtils.createFile(mProject, globalEnviModel.mMainfestDir)
        saveDir.mkdirs()
        def AndroidManifestFile = new File(saveDir, MANIFEST_NAME)
        AndroidManifestFile.createNewFile()
        AndroidManifestFile.write(moduleAndroidManifest)
        return saveDir
    }

    /**
     * merge
     **/
    private String doMerge(TPinModuleModel mainModule, Collection<TPinModuleModel> modules) {
        File mainManifestFile = TPinGradleUtils.createFile(mProject, mainModule.mAndroidSourceSet.mainfestSrcFilePath)

        ManifestMerger2.MergeType mergeType = ManifestMerger2.MergeType.APPLICATION
        XmlDocument.Type documentType = XmlDocument.Type.MAIN
        ManifestMerger2.Invoker invoker = new ManifestMerger2.Invoker(mainManifestFile, TPinUtils.logger, mergeType, documentType)
        invoker.withFeatures(ManifestMerger2.Invoker.Feature.NO_PLACEHOLDER_REPLACEMENT)
        modules.each {
            if (!it.isMainModule) {
                def microManifestFile = TPinGradleUtils.createFile(mProject, it.mAndroidSourceSet.mainfestSrcFilePath)
                if (microManifestFile.exists()) {
                    invoker.addLibraryManifest(microManifestFile)
                    TPinUtils.logInfo("addLibraryManifest " + it.mName, microManifestFile.absolutePath)
                }
            }
        }

        def mergingReport = invoker.merge()
        if (!mergingReport.result.success) {
            throw new GradleException(mergingReport.reportString)
        }
        def moduleAndroidManifest = mergingReport.getMergedDocument(MergingReport.MergedManifestKind.MERGED)
        return moduleAndroidManifest
    }

    void setManifestSourceSet(String filedir) {
        BaseExtension android = TPinModuleEnvironment.getInstance(mProject).getBaseExtension('android')
        android.sourceSets.main.manifest.srcFile filedir
    }



}