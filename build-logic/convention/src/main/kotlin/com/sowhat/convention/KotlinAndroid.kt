package com.sowhat.convention

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.internal.impldep.com.amazonaws.PredefinedClientConfigurations.defaultConfig
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid() {
    // Plugins
    pluginManager.apply("org.jetbrains.kotlin.android")

    // Android settings
    androidExtension.apply {
        compileSdk = 34

        buildFeatures {
            buildConfig = true
        }

        lint {
            checkReleaseBuilds = false
        }

        defaultConfig {
            minSdk = 24

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables {
                useSupportLibrary = true
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
//            isCoreLibraryDesugaringEnabled = true
        }

        buildTypes {
            val naverClientId = project.properties["NAVER_CLIENT_ID"]
            val naverClientSecret = project.properties["NAVER_CLIENT_SECRET"]

            val kakaoNativeAppKey = project.properties["KAKAO_NATIVE_APP_KEY"]
            val kakaoOAuthHost = project.properties["KAKAO_OAUTH_HOST"]

            val googleWebClientId = project.properties["GOOGLE_WEB_CLIENT_ID"]

            getByName("debug") {
                buildConfigField("String", "NAVER_CLIENT_ID", naverClientId.toString())
                buildConfigField("String", "NAVER_CLIENT_SECRET", naverClientSecret.toString())
                buildConfigField("String", "KAKAO_NATIVE_APP_KEY", kakaoNativeAppKey.toString())
                resValue("string", "kakao_oauth_host", kakaoOAuthHost.toString())
                buildConfigField("String", "GOOGLE_WEB_CLIENT_ID", googleWebClientId.toString())
            }

            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
                buildConfigField("String", "NAVER_CLIENT_ID", naverClientId.toString())
                buildConfigField("String", "NAVER_CLIENT_SECRET", naverClientSecret.toString())
                buildConfigField("String", "KAKAO_NATIVE_APP_KEY", kakaoNativeAppKey.toString())
                resValue("string", "kakao_oauth_host", kakaoOAuthHost.toString())
                buildConfigField("String", "GOOGLE_WEB_CLIENT_ID", googleWebClientId.toString())
            }
        }
    }

    configureKotlin()

    dependencies {

    }
}

internal fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
            // Treat all Kotlin warnings as errors (disabled by default)
            // Override by setting warningsAsErrors=true in your ~/.gradle/gradle.properties
            val warningsAsErrors: String? by project
            allWarningsAsErrors = warningsAsErrors.toBoolean()
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlin.RequiresOptIn",
            )
        }
    }
}