// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(libs.google.services)
        // for serialization
        classpath(libs.kotlin.serialization)
    }

    repositories {
        google()
        mavenCentral()
    }
}

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.google.services) apply false

}
true // Needed to make the Suppress annotation work for the plugins block