package com.sowhat.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

internal val Project.applicationExtension: CommonExtension<*, *, *, *>
    get() = extensions.getByType<ApplicationExtension>()

internal val Project.libraryExtension: CommonExtension<*, *, *, *>
    get() = extensions.getByType<LibraryExtension>()

internal val Project.androidExtension: CommonExtension<*, *, *, *>
    get() = runCatching { libraryExtension }
        .recoverCatching { applicationExtension }
        .onFailure { println("Could not find Library or Application extension from this project") }
        .getOrThrow()

internal val ExtensionContainer.libs: VersionCatalog
    get() = getByType<VersionCatalogsExtension>().named("libs")

internal fun DependencyHandlerScope.implementation(libs: VersionCatalog, dependency: String) {
    add("implementation", libs.findLibrary(dependency).get())
}

internal fun DependencyHandlerScope.platformImplementation(libs: VersionCatalog, dependency: String) {
    add("implementation", platform(libs.findLibrary(dependency).get()))
}

internal fun DependencyHandlerScope.kapt(libs: VersionCatalog, dependency: String) {
    add("kapt", libs.findLibrary(dependency).get())
}

fun Project.kapt(configure: Action<KaptExtension>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("kapt", configure)
