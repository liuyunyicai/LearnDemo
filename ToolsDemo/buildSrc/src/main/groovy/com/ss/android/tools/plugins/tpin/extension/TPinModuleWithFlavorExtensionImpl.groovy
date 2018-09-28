package com.ss.android.tools.plugins.tpin.extension

import com.ss.android.tools.plugins.tpin.extension.api.ITPinModuleWithFlavorExtension
import com.ss.android.tools.plugins.tpin.global.TPinModuleEnvironment
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.NamedDomainObjectFactory
import org.gradle.api.Project


class TPinModuleWithFlavorExtensionImpl extends BaseTPinExtensionImpl implements ITPinModuleWithFlavorExtension{

    NamedDomainObjectContainer<TPinModuleConfigImpl> tpinModuleContainer

    FlavoredTPinModuleNDOFactory mFactory

    TPinModuleWithFlavorExtensionImpl(Project project) {
        super(project)
        mFactory = new FlavoredTPinModuleNDOFactory(project)
        tpinModuleContainer = project.container(TPinModuleConfigImpl.class, mFactory)
    }

    @Override
    void config(Action<NamedDomainObjectContainer<TPinModuleConfigImpl>> action) {
        action.execute(tpinModuleContainer)
    }

    @Override
    void debuggable(boolean debug) {
        TPinModuleEnvironment.sDebuggable = debug
    }

    @Override
    void outputManifestDir(String dir) {

    }

    @Override
    void outputRFileDir(String dir) {

    }

    static class FlavoredTPinModuleNDOFactory implements NamedDomainObjectFactory<TPinModuleConfigImpl> {
        Project mProject

        FlavoredTPinModuleNDOFactory(Project project) {
            mProject = project
        }

        @Override
        TPinModuleConfigImpl create(String flavor) {
            return new TPinModuleConfigImpl(mProject, flavor)
        }
    }
}