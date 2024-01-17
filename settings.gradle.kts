pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

rootProject.name = "justsayit"
include(":app")
include(":authentication")
include(":authentication:authentication-presentation")
include(":core")
include(":core:datastore")
include(":core:designsystem")
include(":user")
include(":user:user-data")
include(":user:user-domain")
include(":user:user-presentation")
include(":core:network")
include(":main")
include(":main:main-data")
include(":main:main-domain")
include(":main:main-presentation")
include(":core:common")
include(":authentication:authentication-data")
include(":authentication:authentication-domain")
include(":core:di")
include(":post")
include(":post:post-presentation")
