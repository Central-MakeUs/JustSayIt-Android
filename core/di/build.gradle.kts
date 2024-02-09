@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.sowhat.justsayitt.library") // app 모듈 이외에 안드로이드 라이브러리 형태로 만들어진 모듈은 application이 아닌 library를 활용
    id("com.sowhat.justsayitt.application.common")
    id("com.sowhat.justsayitt.application.hilt")
    id("com.sowhat.justsayitt.application.datastore")
}

android {
    namespace = "com.sowhat.di"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:datastore"))
    implementation(project(":core:database"))
    implementation(project(":authentication:authentication-data"))
    implementation(project(":authentication:authentication-domain"))
    implementation(project(":user:user-data"))
    implementation(project(":user:user-domain"))
    implementation(project(":post:post-data"))
    implementation(project(":post:post-domain"))
    implementation(project(":report:report-data"))
    implementation(project(":report:report-domain"))
    implementation(project(":feed:feed-data"))
    implementation(project(":feed:feed-domain"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:notification"))
}