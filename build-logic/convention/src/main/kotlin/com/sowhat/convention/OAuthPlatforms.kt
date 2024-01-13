package com.sowhat.convention

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureOAuthPlatforms() {
    val libs = extensions.libs
//    Extensions
    pluginManager.apply {
        apply("com.google.gms.google-services")
    }

    dependencies {
        add("implementation", libs.findLibrary("nid.oauth").get())
        implementation(libs, "kakao.sdk")
        implementation(libs, "firebase.analytics")
        platformImplementation(libs, "firebase.bom")
        implementation(libs, "firebase.auth.ktx")
        implementation(libs, "play.services.auth")
    }
}