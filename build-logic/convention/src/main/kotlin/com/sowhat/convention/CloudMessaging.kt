package com.sowhat.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureCloudMessaging() {
    val libs = extensions.libs
//    Extensions
    pluginManager.apply {
        apply("com.google.gms.google-services")
    }

    dependencies {
        implementation(libs, "firebase.cloud.messaging")
    }
}