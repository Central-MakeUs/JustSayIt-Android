package com.sowhat.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureHiltAndroid() {
    pluginManager.apply {
        apply("com.google.dagger.hilt.android")
        apply("kotlin-kapt")
    }

    val libs = extensions.libs
    dependencies {
        add("implementation", libs.findLibrary("hilt.android").get())
        add("implementation", libs.findLibrary("hilt.android.testing").get())
//        add("implementation", libs.findLibrary("hilt.ext.compiler").get())
//        add("implementation", libs.findLibrary("hilt.ext.work").get())
        add("implementation", libs.findLibrary("hilt.navigation.compose").get())
        add("kapt", libs.findLibrary("hilt.compiler").get())
    }

}