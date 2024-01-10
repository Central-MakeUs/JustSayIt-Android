package com.sowhat.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureCommonLibraries() {
    val libs = extensions.libs
//    Extensions
    dependencies {
        add("implementation", libs.findLibrary("coil.compose").get())
        add("implementation", libs.findLibrary("androidx.compose.navigation").get())
        add("implementation", libs.findLibrary("androidx.compose.navigation.test").get())
        add("implementation", libs.findLibrary("androidx.navigation.common.ktx").get())
    }
}