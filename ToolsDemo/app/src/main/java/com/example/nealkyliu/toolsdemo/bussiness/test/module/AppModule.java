package com.example.nealkyliu.toolsdemo.bussiness.test.module;

import com.example.nealkyliu.toolsdemo.bussiness.test.classed.InjectClass;
import com.example.nealkyliu.toolsdemo.bussiness.test.classed.InjectClass2;
import com.example.nealkyliu.toolsdemo.bussiness.test.classed.InjectSetClass;

import org.jetbrains.annotations.TestOnly;

import java.util.HashSet;
import java.util.Set;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;
import dagger.multibindings.IntoMap;
import dagger.multibindings.IntoSet;
import dagger.multibindings.LongKey;
import dagger.multibindings.Multibinds;
import dagger.multibindings.StringKey;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/10
 **/

@Module
public class AppModule {
    @Provides
    @IntoSet
    InjectSetClass provideInjectClass() {
        return new InjectSetClass("11111");
    }

    @Provides
    @IntoSet
    InjectSetClass provideInjectClass2() {
        return new InjectSetClass("22222");
    }

    @Provides
    @ElementsIntoSet
    Set<InjectSetClass> provideInjectClass3() {
        Set<InjectSetClass> set= new HashSet<InjectSetClass>();
        set.add(new InjectSetClass("33333"));
        set.add(new InjectSetClass("44444"));
        set.add(new InjectSetClass("55555"));
        set.add(new InjectSetClass("66666"));
        return set;
    }

    @Provides
    @IntoMap
    @StringKey("StringMapA")
    InjectSetClass proivideMapClass1() {
        return new InjectSetClass("StringMapA");
    }

    @Provides
    @IntoMap
    @StringKey("StringMapB")
    InjectSetClass proivideMapClass2() {
        return new InjectSetClass("StringMapB");
    }

    @Provides
    @IntoMap
    @LongKey(11111)
    InjectSetClass proivideMapClass3() {
        return new InjectSetClass("LongMap1111");
    }

    @Provides
    @IntoMap
    @LongKey(22222)
    InjectSetClass proivideMapClass4() {
        return new InjectSetClass("LongMap2222");
    }

}
