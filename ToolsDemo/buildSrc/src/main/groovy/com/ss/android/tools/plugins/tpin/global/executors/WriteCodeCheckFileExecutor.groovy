package com.ss.android.tools.plugins.tpin.global.executors

import com.android.build.gradle.api.AndroidSourceDirectorySet
import com.ss.android.tools.plugins.tpin.global.executors.base.BaseExecutor
import com.ss.android.tools.plugins.tpin.global.service.context.IExecutorContext
import com.ss.android.tools.plugins.tpin.model.GlobalEnviModel
import com.ss.android.tools.plugins.tpin.model.TPinModuleModel
import com.ss.android.tools.plugins.tpin.utils.TPinGradleUtils
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import org.gradle.api.Project
import org.w3c.dom.Document
import org.w3c.dom.Element

import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class WriteCodeCheckFileExecutor extends BaseExecutor {
    private static final String CODE_CHECK_FILE_NAME = "code-check.xml"

    WriteCodeCheckFileExecutor(Project project) {
        super(project)
    }

    @Override
    void execute(IExecutorContext context) {
        writeFile(context.globalEnviModel, context.pinModules)
    }

//    def getAllFlavorInfo(Collection<TPinModuleModel> modules) {
//        Map<String, List<TPinModuleModel>> map = new HashMap<>()
//
//        modules.each { module ->
//            module.mFlavors.each {
//                List<TPinModuleModel> list = map.get(it)
//                if (null == list) {
//                    list = new ArrayList<>()
//                    map.put(it, list)
//                }
//                list.add(module)
//            }
//        }
//
//        map
//    }

    def writeFile(GlobalEnviModel enviModel, Collection<TPinModuleModel> list) {
        String xmlDir = "$enviModel.mCodeCheckDir"
        File xmlDirFile = TPinGradleUtils.createFile(mProject, xmlDir)
        if (!xmlDirFile.exists()) {
            xmlDirFile.mkdir()
        }

        def file = TPinGradleUtils.createFile(mProject, "$xmlDir/$CODE_CHECK_FILE_NAME")
        if (!file.exists()) {
            file.createNewFile()
        }
        save(file, list)
    }

    void save(File destFile, Collection<TPinModuleModel> modules) {
        TPinUtils.logInfo("******* WriteCodeCheckFileExecutor", destFile.absolutePath)
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance()
        Document documentTemp = builderFactory.newDocumentBuilder().newDocument()
        Element pinModuleXmlTemp = documentTemp.createElement("tpin-module")
        // resources
        modules.each {
            Element resourcesElement = documentTemp.createElement("module")
            resourcesElement.setAttribute("name", it.mName)


            createSrcElement(documentTemp, resourcesElement, "javaSrc"        , it.mAndroidSourceSet.java)
            createSrcElement(documentTemp, resourcesElement, "resSrc"         , it.mAndroidSourceSet.res)
            createSrcElement(documentTemp, resourcesElement, "jniSrc"         , it.mAndroidSourceSet.jni)
            createSrcElement(documentTemp, resourcesElement, "jniLibsSrc"     , it.mAndroidSourceSet.jniLibs)
            createSrcElement(documentTemp, resourcesElement, "aidlSrc"        , it.mAndroidSourceSet.aidl)
            createSrcElement(documentTemp, resourcesElement, "assetsSrc"      , it.mAndroidSourceSet.assets)
            createSrcElement(documentTemp, resourcesElement, "shadersSrc"     , it.mAndroidSourceSet.shaders)
            createSrcElement(documentTemp, resourcesElement, "resourcesSrc"   , it.mAndroidSourceSet.resources)
            createSrcElement(documentTemp, resourcesElement, "renderscriptSrc", it.mAndroidSourceSet.renderscript)

            pinModuleXmlTemp.appendChild(resourcesElement)
        }

        // save
        Transformer transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, "yes")
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
        transformer.transform(new DOMSource(pinModuleXmlTemp), new StreamResult(destFile))
    }


    def createSrcElement(Document documentTemp, Element sourceElement, String name, AndroidSourceDirectorySet set) {
        Element srcElement = documentTemp.createElement(name)
        set.srcDirs.each {
            createAndAddItemElement(documentTemp, srcElement, TPinGradleUtils.getAbsolutePath(mProject, it.absolutePath))
        }
        sourceElement.appendChild(srcElement)
    }

    def createAndAddItemElement(Document documentTemp, Element sourceElement, String value) {
        Element itemElement = documentTemp.createElement("item")
        itemElement.setAttribute("path", value)
        sourceElement.appendChild(itemElement)
    }
}