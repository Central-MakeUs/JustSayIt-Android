@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.sowhat.justsayit.library") // app 모듈 이외에 안드로이드 라이브러리 형태로 만들어진 모듈은 application이 아닌 library를 활용
    id("com.sowhat.justsayit.application.compose")
    id("com.sowhat.justsayit.application.common")
    id("com.sowhat.justsayit.application.oauth")
    id("com.sowhat.justsayit.application.hilt")
}

android {
    namespace = "com.sowhat.authentication_presentation"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
//    implementation(libs.androidx.navigation.common.ktx)
    implementation(project(":core:designsystem"))
    implementation(project(":core:common"))
    implementation(project(":authentication:authentication-domain"))
    implementation(project(":core:di"))
    implementation(project(":core:datastore"))
    implementation(project(":core:network"))
}