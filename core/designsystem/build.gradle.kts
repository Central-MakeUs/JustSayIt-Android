@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.sowhat.justsayit.library") // app 모듈 이외에 안드로이드 라이브러리 형태로 만들어진 모듈은 application이 아닌 library를 활용
    id("com.sowhat.justsayit.application.hilt")
    id("com.sowhat.justsayit.application.datastore")
}

android {
    namespace = "com.sowhat.designsystem"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}