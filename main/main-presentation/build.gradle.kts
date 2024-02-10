@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.sowhat.justsayitt.library") // app 모듈 이외에 안드로이드 라이브러리 형태로 만들어진 모듈은 application이 아닌 library를 활용
    id("com.sowhat.justsayitt.application.compose")
    id("com.sowhat.justsayitt.application.common")
    id("com.sowhat.justsayitt.application.hilt")
}

android {
    namespace = "com.sowhat.main_presentation"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:common"))
    implementation(project(":feed:feed-presentation"))
    implementation(project(":post:post-presentation"))
    implementation(project(":report:report-presentation"))
    implementation(project(":user:user-presentation"))
    implementation(project(":notification:notification-presentation"))

}