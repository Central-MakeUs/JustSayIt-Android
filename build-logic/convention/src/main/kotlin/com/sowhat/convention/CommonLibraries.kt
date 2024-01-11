package com.sowhat.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureCommonLibraries() {
    val libs = extensions.libs

    pluginManager.apply {
        apply("kotlinx-serialization")
        apply("org.jetbrains.kotlin.kapt")
        apply("org.jetbrains.kotlin.plugin.serialization")
        apply("kotlin-kapt")
    }

//    Extensions
    dependencies {
        implementation(libs, "coil.compose")
        implementation(libs, "androidx.compose.navigation")
        implementation(libs, "androidx.compose.navigation.test")
        implementation(libs, "androidx.navigation.common.ktx")

        implementation(libs, "okhttp.logging")
        implementation(libs, "retrofit.core")
        implementation(libs, "retrofit.kotlin.serialization")


        implementation(libs, "room.core")
        implementation(libs, "room.ktx")
        implementation(libs, "room.paging")
        kapt(libs, "room.compiler")


        implementation(libs, "paging.compose")
        implementation(libs, "paging.runtime.ktx")
    }
}