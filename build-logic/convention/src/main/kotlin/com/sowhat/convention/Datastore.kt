package com.sowhat.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureDatastore() {
    pluginManager.apply {
        apply("kotlinx-serialization")
    }

    val libs = extensions.libs
    dependencies {
        add("implementation", libs.findLibrary("kotlinx.serialization").get())
        add("implementation", libs.findLibrary("datastore.core").get())
        add("implementation", libs.findLibrary("datastore").get())
        add("implementation", libs.findLibrary("kotilnx.collections.immutable").get())
    }

}