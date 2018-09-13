package com.ss.android.tools.plugins.core

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList

import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class TPinManifest {

    Document document
    Element rootElement

    String packageName
    Map<String, ResourceFile> lastModifiedResourcesMap
    Map<String, ResourceFile> lastModifiedClassesMap

    void load(File sourceFile) {
        if (!sourceFile.exists()) return
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance()
        document = builderFactory.newDocumentBuilder().parse(sourceFile)
        rootElement = document.documentElement
        packageName = rootElement.getAttribute("package")
    }

    String getPackageName() {
        if (rootElement == null) return ""

        return rootElement.getAttribute("package")
    }

    long getResourcesLastModified() {
        if (rootElement == null) return 0

        Element resourcesElement = (Element) rootElement.getElementsByTagName("resources").item(0)
        return resourcesElement.getAttribute("last-modified").toLong()
    }

    void setResourcesLastModified(long lastModified) {
        resourcesLastModified = lastModified
    }

    Map<String, Long> getResourcesMap() {
        if (lastModifiedResourcesMap != null) return lastModifiedResourcesMap

        lastModifiedResourcesMap = new HashMap<>()
        if (rootElement == null) return lastModifiedResourcesMap

        NodeList resourcesNodeList = rootElement.getElementsByTagName("resources")
        if (resourcesNodeList.length == 0) {
            return lastModifiedResourcesMap
        }
        Element resourcesElement = (Element) resourcesNodeList.item(0)
        NodeList fileNodeList = resourcesElement.getElementsByTagName("file")
        for (int i = 0; i < fileNodeList.getLength(); i++) {
            Element fileElement = (Element) fileNodeList.item(i)
            ResourceFile resourceFile = new ResourceFile()
            resourceFile.name = fileElement.getAttribute("name")
            resourceFile.pinModuleName = fileElement.getAttribute("pinModuleName")
            resourceFile.path = fileElement.getAttribute("path")
            resourceFile.lastModified = fileElement.getAttribute("lastModified").toLong()
            lastModifiedResourcesMap.put(resourceFile.path, resourceFile)
        }
        return lastModifiedResourcesMap
    }

    Map<String, ResourceFile> getClassesMap() {
        if (lastModifiedClassesMap != null) return lastModifiedClassesMap

        lastModifiedClassesMap = new HashMap<>()
        if (rootElement == null) return lastModifiedClassesMap

        NodeList classesNodeList = rootElement.getElementsByTagName("classes")
        if (classesNodeList.length == 0) {
            return lastModifiedClassesMap
        }
        Element classesElement = (Element) classesNodeList.item(0)
        NodeList fileNodeList = classesElement.getElementsByTagName("file")
        for (int i = 0; i < fileNodeList.getLength(); i++) {
            Element fileElement = (Element) fileNodeList.item(i)
            ResourceFile resourceFile = new ResourceFile()
            resourceFile.name = fileElement.getAttribute("name")
            resourceFile.pinModuleName = fileElement.getAttribute("pinModuleName")
            resourceFile.path = fileElement.getAttribute("path")
            resourceFile.lastModified = fileElement.getAttribute("lastModified").toLong()
            lastModifiedClassesMap.put(resourceFile.path, resourceFile)
        }
        return lastModifiedClassesMap
    }

    void save(File destFile) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance()
        Document documentTemp = builderFactory.newDocumentBuilder().newDocument()
        Element pinModuleXmlTemp = documentTemp.createElement("tpin-module")
        pinModuleXmlTemp.setAttribute("package", packageName)
        // resources
        Element resourcesElement = documentTemp.createElement("resources")
        pinModuleXmlTemp.appendChild(resourcesElement)
        if (lastModifiedResourcesMap != null) {
            lastModifiedResourcesMap.each {
                ResourceFile resourceFile = it.value
                Element fileElement = documentTemp.createElement("file")
                fileElement.setAttribute("name", resourceFile.name)
                fileElement.setAttribute("path", resourceFile.path)
                fileElement.setAttribute("lastModified", resourceFile.lastModified.toString())
                fileElement.setAttribute("pinModuleName", resourceFile.pinModuleName)
                resourcesElement.appendChild(fileElement)
            }
        }

        // classes
        if (lastModifiedClassesMap != null) {
            Element classesElement = documentTemp.createElement("classes")
            pinModuleXmlTemp.appendChild(classesElement)
            lastModifiedClassesMap.each {
                ResourceFile resourceFile = it.value
                Element fileElement = documentTemp.createElement("file")
                fileElement.setAttribute("name", resourceFile.name)
                fileElement.setAttribute("path", resourceFile.path)
                fileElement.setAttribute("lastModified", resourceFile.lastModified.toString())
                fileElement.setAttribute("pinModuleName", resourceFile.pinModuleName)
                classesElement.appendChild(fileElement)
            }
            pinModuleXmlTemp.appendChild(classesElement)
        } else if (rootElement != null) {
            NodeList classesNodeList = rootElement.getElementsByTagName("classes")
            if (classesNodeList.length == 1) {
                Element classesElement = (Element) classesNodeList.item(0)
                pinModuleXmlTemp.appendChild(documentTemp.importNode(classesElement, true))
            }
        }
        // save
        Transformer transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, "yes")
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
        transformer.transform(new DOMSource(pinModuleXmlTemp), new StreamResult(destFile))
    }
}