@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.sowhat.justsayit.application")
    id("com.sowhat.justsayit.application.hilt")
}

android {
    namespace = "com.sowhat.datastore"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}