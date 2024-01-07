package com.sowhat.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureComposeAndroid() {
    val libs = extensions.libs
    androidExtension.apply {
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion =
                libs.findVersion("androidxComposeCompiler").get().toString() // in libs.versions
        }

        dependencies {
            val bom = libs.findLibrary("compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))

            add("implementation", libs.findLibrary("material3").get())
            add("implementation", libs.findLibrary("ui").get())
            add("implementation", libs.findLibrary("ui.tooling.preview").get())
            add("androidTestImplementation", libs.findLibrary("androidx.test.ext.junit").get())
            add("androidTestImplementation", libs.findLibrary("espresso.core").get())
            add("androidTestImplementation", libs.findLibrary("ui.test.junit4").get())
            add("debugImplementation", libs.findLibrary("ui.tooling").get())
            add("debugImplementation", libs.findLibrary("ui.test.manifest").get())
        }
    }
}