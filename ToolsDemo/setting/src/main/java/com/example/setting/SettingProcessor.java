package com.example.setting;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/22
 **/
@AutoService(Processor.class)
public class SettingProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(SettingValue.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        TypeSpec.Builder buidler = TypeSpec.classBuilder("AppSetting")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(generateEntity());

        addPrimaryKey(buidler, "id");

        for (int i = 0; i < 998; i++) {
            String filedName = "settingKey" + i;
            addFiledAndSetterGetter(buidler, filedName, String.class);
        }

        TypeSpec appSetting = buidler.build();
        JavaFile javaFile = JavaFile.builder("com.example.helloworld", appSetting)
                .build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 生成Entity
     **/
    private AnnotationSpec generateEntity() {
        return AnnotationSpec.builder(Entity.class)
                .addMember("tableName", "$S", "appsetting")
                .build();
    }


    /**
     * 添加PrimaryKey
     **/
    private void addPrimaryKey(@NonNull TypeSpec.Builder builder, @NonNull String key) {
        Class clazz = int.class;
        builder.addField(generatePrimaryKey(key, clazz));
        builder.addMethod(generateSetterMethod(key, clazz));
        builder.addMethod(generateGetterMethod(key, clazz));
    }

    /**
     * 生成PrimaryKey
     **/
    private FieldSpec generatePrimaryKey(@NonNull String key, @NonNull Class clazz) {
        AnnotationSpec annotation = AnnotationSpec.builder(PrimaryKey.class)
                .addMember("autoGenerate", "true")
                .build();
        FieldSpec field = FieldSpec.builder(clazz, key)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(annotation)
                .build();

        return field;
    }

    /**
     * 添加变量及getter setter函数
     **/
    private void addFiledAndSetterGetter(@NonNull TypeSpec.Builder builder, @NonNull String fieldName, @NonNull Class clazz) {
        builder.addField(generateFiled(fieldName, clazz));
        builder.addMethod(generateSetterMethod(fieldName, clazz));
        builder.addMethod(generateGetterMethod(fieldName, clazz));
    }

    /**
     * 生成setting变量
     **/
    private FieldSpec generateFiled(@NonNull String fieldName, @NonNull Class clazz) {
        AnnotationSpec annotation = AnnotationSpec.builder(ColumnInfo.class)
                .addMember("name", "$S", fieldName)
                .build();

        FieldSpec field = FieldSpec.builder(clazz, fieldName)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(annotation)
                .build();

        return field;
    }


    /**
     * 生成setter
     **/
    private MethodSpec generateSetterMethod(@NonNull String fieldName, @NonNull Class clazz) {
        String methodName = "set" + firstCharToUpper(fieldName);

        MethodSpec method = MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(clazz, "value")
                .addStatement("this.$N = value", fieldName)
                .build();

        return method;
    }

    /**
     * 生成getter
     **/
    private MethodSpec generateGetterMethod(@NonNull String fieldName, @NonNull Class clazz) {
        String methodName = "get" + firstCharToUpper(fieldName);

        MethodSpec method = MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(clazz)
                .addStatement("return this.$N", fieldName)
                .build();

        return method;
    }


    /**
     * 首字母大写
     **/
    public static String firstCharToUpper(String name) {
        char[] cs = name.toCharArray();

        if (cs[0] >= 'a' && cs[0] <= 'z') {
            cs[0] -= 32;
        }
        return String.valueOf(cs);
    }

}
