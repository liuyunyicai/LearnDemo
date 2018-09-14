package com.ss.android.tools.plugins.tpin.extension

import com.android.build.gradle.api.AndroidSourceDirectorySet
import com.android.build.gradle.api.AndroidSourceSet
import com.android.build.gradle.internal.api.DefaultAndroidSourceSet
import com.android.build.gradle.internal.dsl.AndroidSourceSetFactory
import com.ss.android.tools.plugins.tpin.global.TPinModuleEnvironment
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

class TPinModuleConfigExtensionImpl extends BaseTPinExtensionImpl implements ITPinModuleConfigExtension {

    NamedDomainObjectContainer<AndroidSourceSet> sourceSetsContainer

    TPinModuleConfigExtensionImpl(Project project) {
        super(project)
        sourceSetsContainer = project.container(AndroidSourceSet.class,
                new AndroidSourceSetFactory(project.objects, project, false))
    }

    @Override
    void sourceSet(Action<AndroidSourceSet> action) {
        TPinUtils.logInfo("******* ", TPinModuleEnvironment)

        action.execute(new SourceSetImpl("count", mProject, false))
    }

    @Override
    void testSourceSets(Action<NamedDomainObjectContainer<AndroidSourceSet>> action) {
        action.execute(sourceSetsContainer)
    }

    static class SourceSetImpl extends DefaultAndroidSourceSet{

        SourceSetImpl(String name, Project project, boolean publishPackage) {
            super(name, project, publishPackage)
        }

        @Override
        AndroidSourceDirectorySet getJava() {
            TPinUtils.logInfo("TPinModuleConfigExtensionImpl", "sourceSet Name ")
            return super.getJava()
        }
    }
}