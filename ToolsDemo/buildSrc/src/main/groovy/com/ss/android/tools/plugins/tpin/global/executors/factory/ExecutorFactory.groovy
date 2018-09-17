package com.ss.android.tools.plugins.tpin.global.executors.factory

import com.ss.android.tools.plugins.tpin.global.executors.ApplyBuildExecutor
import com.ss.android.tools.plugins.tpin.global.executors.GenerateRExecutor
import com.ss.android.tools.plugins.tpin.global.executors.MergeManifestExecutor
import com.ss.android.tools.plugins.tpin.global.executors.MinusSourceSetExecutor
import com.ss.android.tools.plugins.tpin.global.executors.SetSourceSetExecutor
import com.ss.android.tools.plugins.tpin.global.executors.WriteCodeCheckFileExecutor
import com.ss.android.tools.plugins.tpin.global.executors.base.BaseExecutor
import com.ss.android.tools.plugins.tpin.utils.TPinUtils
import org.gradle.api.Project

class ExecutorFactory {

    final static String KEY_APPLY_BUILD_EXECUTOR            = "KEY_APPLY_BUILD_EXECUTOR"
    final static String KEY_GENERATE_R_EXECUTOR             = "KEY_GENERATE_R_EXECUTOR"
    final static String KEY_MERGE_MANIFEST_EXECUTOR         = "KEY_MERGE_MANIFEST_EXECUTOR"
    final static String KEY_SET_SOURCESET_EXECUTOR          = "KEY_SET_SOURCESET_EXECUTOR"
    final static String KEY_MINUS_SOURCESET_EXECUTOR        = "KEY_MINUS_SOURCESET_EXECUTOR"
    final static String KEY_WRITE_CODE_CHECK_FILE_EXECUTOR = "KEY_WRITE_CODE_CHECK_FILE_EXECUTOR"

    private static Map<String, BaseExecutor> sExecutors = new HashMap<>()

    static BaseExecutor getExecutor(Project project, String name) {
        BaseExecutor executor = sExecutors.get(name)

        if (null == executor) {
            executor = build(project, name)
            sExecutors.put(name, executor)
        }
        return executor
    }

    private static BaseExecutor build(Project project, String name) {
        switch (name) {
            case KEY_APPLY_BUILD_EXECUTOR:
                return new ApplyBuildExecutor(project)
            case KEY_GENERATE_R_EXECUTOR:
                return new GenerateRExecutor(project)
            case KEY_MERGE_MANIFEST_EXECUTOR:
                return new MergeManifestExecutor(project)
            case KEY_SET_SOURCESET_EXECUTOR:
                return new SetSourceSetExecutor(project)
            case KEY_MINUS_SOURCESET_EXECUTOR:
                return new MinusSourceSetExecutor(project)
            case KEY_WRITE_CODE_CHECK_FILE_EXECUTOR:
                return new WriteCodeCheckFileExecutor(project)
        }
        TPinUtils.throwException("Code Wrong, Wrong Executor type")

        return null
    }

}