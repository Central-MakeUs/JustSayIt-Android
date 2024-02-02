package com.sowhat.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureRoom() {
    pluginManager.apply {
        apply("kotlin-kapt")
    }

    kapt {
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    val libs = extensions.libs
    dependencies {
        implementation(libs, "room.core")
        implementation(libs, "room.ktx")
        implementation(libs, "room.paging")
        kapt(libs, "room.compiler")
    }

}