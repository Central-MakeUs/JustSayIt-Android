@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("AndroidApplicationPlugin") {
            id = "com.sowhat.justsayit.application"
//            패키지 위치를 포함하여야 함. 여기서는 kotlin(최상위)에 넣었기에 앞에 패키지 경로를 붙이지 않음.
//            만약 convention 내부에 넣었다면, com.sowhat.convention.AndroidApplicationConventionPlugin
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("AndroidLibraryPlugin") {
            id = "com.sowhat.justsayit.library"
//            패키지 위치를 포함하여야 함. 여기서는 kotlin(최상위)에 넣었기에 앞에 패키지 경로를 붙이지 않음.
//            만약 convention 내부에 넣었다면, com.sowhat.convention.AndroidApplicationConventionPlugin
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("AndroidApplicationComposePlugin") {
            id = "com.sowhat.justsayit.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("OAuthPlatformPlugin") {
            id = "com.sowhat.justsayit.application.oauth"
            implementationClass = "OAuthPlatformConventionPlugin"
        }
        register("HiltAndroidPlugin") {
            id = "com.sowhat.justsayit.application.hilt"
            implementationClass = "HiltAndroidConventionPlugin"
        }
        register("DatastorePlugin") {
            id = "com.sowhat.justsayit.application.datastore"
            implementationClass = "DatastoreConventionPlugin"
        }
        register("CommonLibraryPlugin") {
            id = "com.sowhat.justsayit.application.common"
            implementationClass = "CommonLibrariesConventionPlugin"
        }
    }
}