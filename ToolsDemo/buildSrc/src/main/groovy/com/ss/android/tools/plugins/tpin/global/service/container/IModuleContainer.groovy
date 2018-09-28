package com.ss.android.tools.plugins.tpin.global.service.container

import com.android.annotations.NonNull
import com.ss.android.tools.plugins.tpin.model.TPinModuleModel

import javax.annotation.Nullable


interface IModuleContainer<T> {

    /**
     * 如果相同module name已经添加，则会覆盖
     **/
    void add(@NonNull T t)

    void addMain(@NonNull T t)

    void add(@NonNull T t, boolean isMainModule)


    /**
     * 如果相同module name已经添加，则会抛异常
     **/
    void addOrThrow(@NonNull T t)

    void addMainOrThrow(@NonNull T t)

    void addOrThrow(@NonNull T t, boolean isMainModule)



    void remove(@NonNull T t)

    void remove(@NonNull String name)

    void clear()



    @Nullable
    T get(int index)

    @Nullable
    T getByName(@NonNull String name)



    T getMain()

    Collection<T> getAlls()

    Collection<T> getPeers()


    int size()


    boolean isAdded(String name)

    boolean isAdded(T t)

    boolean isMainSetted()


    boolean isMain(T t)

    boolean isMain(String name)
}