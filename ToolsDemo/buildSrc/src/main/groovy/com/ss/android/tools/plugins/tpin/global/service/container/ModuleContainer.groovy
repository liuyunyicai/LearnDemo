package com.ss.android.tools.plugins.tpin.global.service.container

import com.android.annotations.NonNull
import com.ss.android.tools.plugins.tpin.model.TPinModuleModel
import com.ss.android.tools.plugins.tpin.utils.TPinUtils

class ModuleContainer implements IModuleContainer<TPinModuleModel> {

    // key -- moduleName  value -- module index in
    private Map<String, Integer> mModuleNameMap = new HashMap<>()

    // index = 0 为mainModule； 普通module 添加从 index = 1开始// mainModule未添加时使用占位符
    private List<TPinModuleModel> mPinModules = new ArrayList<>()

    private static final int MAIN_MODULE_INDEX = 0

    ModuleContainer() {
        addMainModulePlaceHolder()
    }


    /*=============================== ADD ================================*/
    @Override
    void add(@NonNull TPinModuleModel tPinModuleModel) {
        add(tPinModuleModel, false)
    }

    @Override
    void addMain(@NonNull TPinModuleModel tPinModuleModel) {
        add(tPinModuleModel, true)
    }

    @Override
    void add(@NonNull TPinModuleModel tPinModuleModel, boolean isMainModule) {
        if (isAdded(tPinModuleModel)) {
            remove(tPinModuleModel)
        }
        doAdd(tPinModuleModel, isMainModule)
    }

    @Override
    void addOrThrow(@NonNull TPinModuleModel tPinModuleModel) {
        addOrThrow(tPinModuleModel, false)
    }

    @Override
    void addMainOrThrow(@NonNull TPinModuleModel tPinModuleModel) {
        addOrThrow(tPinModuleModel, true)
    }

    @Override
    void addOrThrow(@NonNull TPinModuleModel tPinModuleModel, boolean isMainModule) {
        if (isAdded(tPinModuleModel)) {
            TPinUtils.throwException("ModuleContainer add two same modules")
        }
        doAdd(tPinModuleModel, isMainModule)
    }

    private void doAdd(@NonNull TPinModuleModel tPinModuleModel, boolean isMainModule) {
        TPinUtils.logInfo("Add module into Container", this, tPinModuleModel.mName, isMainModule)

        if (isMainModule) {
            doAddMain(tPinModuleModel)
        } else {
            doAddPeer(tPinModuleModel)
        }
    }

    private void doAddMain(@NonNull TPinModuleModel tPinModuleModel) {
        mModuleNameMap.put(tPinModuleModel.mName, MAIN_MODULE_INDEX)

        // remove Main
        mPinModules.remove(MAIN_MODULE_INDEX)

        // add main
        mPinModules.add(MAIN_MODULE_INDEX, tPinModuleModel)
    }

    // 添加MainModule占位符
    private void addMainModulePlaceHolder() {
        mPinModules.add(MAIN_MODULE_INDEX, TPinModuleModel.getFakeModule())
    }


    /*=============================== REMOVE ================================*/
    private void doAddPeer(@NonNull TPinModuleModel tPinModuleModel) {
        def startIndex = size()
        mModuleNameMap.put(tPinModuleModel.mName, startIndex)
        mPinModules.add(tPinModuleModel)
    }

    @Override
    void remove(@NonNull TPinModuleModel tPinModuleModel) {
        remove(tPinModuleModel.mName)
    }

    @Override
    void remove(@NonNull String name) {
        if (!isAdded(name)) {
            return
        }

        if (isMain(name)) {
            doRemoveMain(name)
        } else {
            doRemovePeer(name)
        }
    }

    @Override
    void clear() {
        mModuleNameMap.clear()
        mPinModules.clear()

        addMainModulePlaceHolder()
    }

    private void doRemoveMain(String name) {
        mModuleNameMap.remove(name)

        mPinModules.remove(MAIN_MODULE_INDEX)

        addMainModulePlaceHolder()
    }

    private void doRemovePeer(String name) {
        mPinModules.remove(getModuleIndex(name))

        mModuleNameMap.remove(name)
    }


    /*=============================== GET ================================*/
    @Override
    TPinModuleModel get(int index) {
        if (index < 0 || index >= mPinModules.size()) {
            return null
        }
        return mPinModules.get(index)
    }

    @Override
    TPinModuleModel getByName(@NonNull String name) {
        return get(getModuleIndex(name))
    }

    @Override
    TPinModuleModel getMain() {
        def module = get(MAIN_MODULE_INDEX)

        return module.isFake ? null : module
    }

    @Override
    Collection<TPinModuleModel> getAlls() {
        TPinUtils.logInfo(this, "getAlls", isMainSetted())
        if (isMainSetted()) {
            return TPinUtils.getMutableNewList(mPinModules)
        }
        return getPeers()
    }

    @Override
    Collection<TPinModuleModel> getPeers() {
        return TPinUtils.getMutableNewList(mPinModules.subList(MAIN_MODULE_INDEX + 1, mPinModules.size()))
    }

    private int getModuleIndex(String name) {
        return mModuleNameMap.getOrDefault(name, -1)
    }

    @Override
    int size() {
        if (isMainSetted()) {
            return mPinModules.size()
        }

        return mPinModules.size() - 1
    }

    /*=============================== TOOLS ================================*/
    @Override
    boolean isAdded(String name) {
        return mModuleNameMap.containsKey(name)
    }

    @Override
    boolean isAdded(TPinModuleModel tPinModuleModel) {
        return isAdded(tPinModuleModel.mName)
    }

    @Override
    boolean isMainSetted() {
        return getMain() != null
    }

    @Override
    boolean isMain(TPinModuleModel tPinModuleModel) {
        return isMain(tPinModuleModel.mName)
    }

    @Override
    boolean isMain(String name) {
        return getModuleIndex(name) == MAIN_MODULE_INDEX
    }
}