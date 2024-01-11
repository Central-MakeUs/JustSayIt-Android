@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.sowhat.justsayit.library") // app 모듈 이외에 안드로이드 라이브러리 형태로 만들어진 모듈은 application이 아닌 library를 활용
    id("com.sowhat.justsayit.application.common")
    id("com.sowhat.justsayit.application.hilt")
}

android {
    namespace = "com.sowhat.network"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}