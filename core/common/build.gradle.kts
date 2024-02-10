@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.sowhat.justsayitt.library") // app 모듈 이외에 안드로이드 라이브러리 형태로 만들어진 모듈은 application이 아닌 library를 활용
    id("com.sowhat.justsayitt.application.common")
    id("com.sowhat.justsayitt.application.compose")
    id("com.sowhat.justsayitt.application.oauth")
}

android {
    namespace = "com.sowhat.common"

    buildFeatures {
        compose = true
    }

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}