package com.ss.android.tools.plugins.core

import org.gradle.api.GradleScriptException
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.w3c.dom.Element
import org.w3c.dom.NodeList

class TPinModuleCodeCheck {

    Project project
    String projectPath
    File buildDir
    DefaultTPinModuleExtension pinModuleExtension

    TPinManifest microManifest
    ResourceMerged resourceMerged

    Map<String, List<String>> pinModuleReferenceMap

    String errorMessage = ""
    String lineSeparator = System.getProperty("line.separator")

    public TPinModuleCodeCheck(Project project, Map<String, List<String>> pinModuleReferenceMap) {
        this.project = project
        this.pinModuleReferenceMap = pinModuleReferenceMap
        projectPath = project.projectDir.absolutePath
        buildDir = new File(project.projectDir, "build")

//        pinModuleExtension = project.extensions.getByName("tpinModules")

        microManifest = getMicroManifest()
    }

    @TaskAction
    void checkResources(String mergeTaskName) {
        println ":${project.name}:codeCheckResources"
        resourceMerged = new ResourceMerged()
        resourceMerged.load(project.projectDir, mergeTaskName)
        if (!resourceMerged.resourcesMergerFile.exists()) {
            println "[micro-module-code-check] - resourcesMergerFile is not exists!"
            return
        }

        NodeList resourcesNodeList = resourceMerged.getResourcesNodeList()
        List<File> modifiedResourcesList = getModifiedResourcesList(resourcesNodeList)
        if (modifiedResourcesList.size() == 0) {
            return
        }
        handleModifiedResources(modifiedResourcesList)
        if (errorMessage != "") {
            throw new GradleScriptException(errorMessage, null)
        }
        String packageName = getMainManifest().getPackageName()
        microManifest.packageName = packageName
        saveMicroManifest()
    }

    List<File> getModifiedResourcesList(NodeList resourcesNodeList) {
        Map<String, ResourceFile> lastModifiedResourcesMap = microManifest.getResourcesMap()
        List<File> modifiedResourcesList = new ArrayList<>()
        if (resourcesNodeList == null || resourcesNodeList.length == 0) return modifiedResourcesList

        for (int i = 0; i < resourcesNodeList.getLength(); i++) {
            Element resourcesElement = (Element) resourcesNodeList.item(i)
            NodeList fileNodeList = resourcesElement.getElementsByTagName("file")
            for (int j = 0; j < fileNodeList.getLength(); j++) {
                Element fileElement = (Element) fileNodeList.item(j)
                String filePath = fileElement.getAttribute("path")
                if (filePath != null && filePath.endsWith(".xml")) {
                    File file = project.file(filePath)
                    ResourceFile resourceFile = lastModifiedResourcesMap.get(filePath)
                    def currentModified = file.lastModified()
                    if (resourceFile == null || resourceFile.lastModified.longValue() < currentModified) {
                        modifiedResourcesList.add(file)

                        if (resourceFile == null) {
                            resourceFile = new ResourceFile()
                            resourceFile.name = file.name
                            resourceFile.path = filePath
                            resourceFile.pinModuleName = getPinModuleName(filePath)
                            lastModifiedResourcesMap.put(filePath, resourceFile)
                        }
                        resourceFile.lastModified = currentModified
                    }
                }
            }
        }
        return modifiedResourcesList
    }

    void handleModifiedResources(List<File> modifiedResourcesList) {
        Map<String, String> resourcesMap = resourceMerged.getResourcesMap()
        def resourcesPattern = /@(dimen|drawable|color|string|style|id|mipmap|layout)\/[A-Za-z0-9_]+/
        modifiedResourcesList.each {
            String text = it.text
            List<String> textLines = text.readLines()
            def matcher = (text =~ resourcesPattern)
            def absolutePath = it.absolutePath
            def pinModuleName = getPinModuleName(absolutePath)
            while (matcher.find()) {
                def find = matcher.group()
                def name = find.substring(find.indexOf("/") + 1)
                def from = resourcesMap.get(name)

                if (from != null && pinModuleName != from && !isReference(pinModuleName, from)) {
                    List<Number> lines = textLines.findIndexValues { it.contains(find) }
                    lines.each {
                        def lineIndex = it.intValue()
                        def lineContext = textLines.get(lineIndex).trim()
                        if (lineContext.startsWith("<!--")) {
                            return
                        }

                        def message = absolutePath + ':' + (lineIndex + 1)
                        if(!errorMessage.contains(message)) {
                            message += lineSeparator
                            message += "- can't use [" + find + "] which from microModule '${from}'."
                            message += lineSeparator
                            errorMessage += message
                        }
                    }
                }
            }
        }
    }

    @TaskAction
    void checkClasses(String productFlavorBuildType, mergeTaskName) {
        println ":${project.name}:codeCheckClasses"
        File classesMergerFile = new File(buildDir, "intermediates/classes/${productFlavorBuildType}/" + microManifest.packageName.replace(".", "/"))
        if (!classesMergerFile.exists()) {
            println "[micro-module-code-check] - classesMergerFile is not exists!"
            return
        }

        List<File> modifiedClassesList = getModifiedClassesList()
        if (modifiedClassesList.size() == 0) {
            return
        }


        if (resourceMerged == null) {
            resourceMerged = new ResourceMerged()
            resourceMerged.load(project.projectDir, mergeTaskName)
            if (!resourceMerged.resourcesMergerFile.exists()) {
                println "[micro-module-code-check] - " + mergeTaskName + ' is not exists!'
                return
            }
        }
        handleModifiedClasses(modifiedClassesList)
        if (errorMessage != "") {
            throw new GradleScriptException(errorMessage, null)
        }
        saveMicroManifest()
    }

    List<File> getModifiedClassesList() {
        Map<String, ResourceFile> lastModifiedClassesMap = microManifest.getClassesMap()
        List<File> modifiedClassesList = new ArrayList<>()
        File javaDir = new File(pinModuleExtension.mainPinModule.pinModuleDir, "/src/main/java")
        getModifiedJavaFile(javaDir, modifiedClassesList, lastModifiedClassesMap)

        pinModuleExtension.includeTpinModules.each {
            javaDir = new File(it.pinModuleDir, "/src/main/java")
            getModifiedJavaFile(javaDir, modifiedClassesList, lastModifiedClassesMap)
        }
        return modifiedClassesList
    }

    void getModifiedJavaFile(File directory, List<File> modifiedClassesList, Map<String, ResourceFile> lastModifiedClassesMap) {
        directory.listFiles().each {
            if (it.isDirectory()) {
                getModifiedJavaFile(it, modifiedClassesList, lastModifiedClassesMap)
            } else {
                def currentModified = it.lastModified()
                ResourceFile resourceFile = lastModifiedClassesMap.get(it.absolutePath)
                if (resourceFile == null || resourceFile.lastModified.longValue() < currentModified) {
                    modifiedClassesList.add(it)

                    if (resourceFile == null) {
                        resourceFile = new ResourceFile()
                        resourceFile.name = it.name
                        resourceFile.path = it.absolutePath
                        resourceFile.pinModuleName = getPinModuleName(it.absolutePath)
                        lastModifiedClassesMap.put(it.absolutePath, resourceFile)
                    }
                    resourceFile.lastModified = it.lastModified()
                }
            }
        }
    }

    void handleModifiedClasses(List<File> modifiedClassesList) {
        Map<String, String> resourcesMap = resourceMerged.getResourcesMap()
        Map<String, String> classesMap = new HashMap<>()
        microManifest.getClassesMap().each {
            ResourceFile resourceFile = it.value
            def path = resourceFile.path
            def name = path.substring(path.indexOf("java") + 5, path.lastIndexOf(".")).replace(File.separator, ".")
            classesMap.put(name, resourceFile.pinModuleName)
        }

        def resourcesPattern = /R.(dimen|drawable|color|string|style|id|mipmap|layout).[A-Za-z0-9_]+|import\s[A-Za-z0-9_.]+/
        modifiedClassesList.each {
            String text = it.text
            List<String> textLines = text.readLines()
            def matcher = (text =~ resourcesPattern)
            def absolutePath = it.absolutePath
            def pinModuleName = getPinModuleName(absolutePath)
            while (matcher.find()) {
                matcher
                def find = matcher.group()
                def from, name
                if (find.startsWith("R")) {
                    name = find.substring(find.lastIndexOf(".") + 1)
                    from = resourcesMap.get(name)
                } else if (find.startsWith("import")) {
                    name = find.substring(find.lastIndexOf(" ") + 1, find.length())
                    from = classesMap.get(name)
                }

                if (from != null && pinModuleName != from && !isReference(pinModuleName, from)) {
                    List<Number> lines = textLines.findIndexValues { it.contains(find) }
                    lines.each {
                        def lineIndex = it.intValue()
                        def lineContext = textLines.get(lineIndex).trim()
                        if (lineContext.startsWith("//") || lineContext.startsWith("/*")) {
                            return
                        }

                        def message = absolutePath + ':' + (lineIndex + 1)
                        if(!errorMessage.contains(message)) {
                            message += lineSeparator
                            message += "- can't use [" + find + "] which from pinModule '${from}'."
                            message += lineSeparator
                            errorMessage += message
                        }
                    }
                }
            }
        }
    }

    // ----------------------------------------------------------------------------------------

    String getPinModuleName(absolutePath) {
        String moduleName = absolutePath.replace(projectPath, "")
        moduleName = moduleName.substring(0, moduleName.indexOf(ResourceMerged.SRC))
        if (File.separator == "\\") {
            moduleName = moduleName.replaceAll("\\\\", ":")
        } else {
            moduleName = moduleName.replaceAll("/", ":")
        }
        return moduleName
    }

    boolean isReference(String pinModuleName, String from) {
        List<String> original = new ArrayList<>()
        original.add(pinModuleName)
        return isReference(pinModuleName, from, original)
    }

    boolean isReference(String pinModuleName, String from, List<String> original) {
        List<String> referenceList = pinModuleReferenceMap.get(pinModuleName)
        if (referenceList == null) return false
        if (referenceList.contains(from)) {
            return true
        }
        for (int i = 0; i < referenceList.size(); i++) {
            if (original.contains(referenceList[i])) {
                continue
            } else {
                original.add(referenceList[i])
            }
            if (isReference(referenceList[i], from, original)) {
                return true
            }
        }
        return false
    }

    String getMicroModulePackageName(File directory, String packageName) {
        if (directory == null) return packageName

        File[] files = directory.listFiles()
        if (files == null || files.length == 0) {
            return packageName + "." + directory.name
        } else if (files.length == 1) {
            if (files[0].isFile()) {
                return packageName + "." + directory.name
            } else {
                return getMicroModulePackageName(files[0], packageName + "." + directory.name)
            }
        } else {
            for (int i = 0; i < files.size(); i++) {
                if (files[i].isFile()) {
                    return packageName + "." + directory.name
                }
            }
        }
    }

    private AndroidManifest getMainManifest() {
        AndroidManifest mainManifest = new AndroidManifest()
        def manifest = new File(pinModuleExtension.mainPinModule.pinModuleDir, "src/main/AndroidManifest.xml")
        mainManifest.load(manifest)
        return mainManifest
    }

    private TPinManifest getMicroManifest() {
        TPinManifest microManifest = new TPinManifest()
        microManifest.load(project.file("build/tpinModule/code-check-manifest.xml"))
        return microManifest
    }

    private TPinManifest saveMicroManifest() {
        if (microManifest == null) {
            microManifest = new TPinManifest()
        }
        return microManifest.save(project.file("build/tpinModule/code-check-manifest.xml"))
    }

}