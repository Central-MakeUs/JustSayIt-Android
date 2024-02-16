@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("com.sowhat.justsayitt.application")
    id("com.sowhat.justsayitt.application.compose")
    id("com.sowhat.justsayitt.application.oauth")
    id("com.sowhat.justsayitt.application.datastore")
    id("com.sowhat.justsayitt.application.hilt")
    id("com.sowhat.justsayitt.application.common")
}

android {
    signingConfigs {
        create("release") {
        }
    }
    namespace = "com.sowhat.justsayitt"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sowhat.justsayitt"
        targetSdk = 34
        versionCode = 4
        versionName = "1.0.1"

    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.identity.credential.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(project(":core:datastore"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:notification"))
    implementation(project(":authentication:authentication-presentation"))
    implementation(project(":user:user-presentation"))
    implementation(project(":main:main-presentation"))
    implementation(project(":core:common"))
    implementation(project(":post:post-presentation"))
}