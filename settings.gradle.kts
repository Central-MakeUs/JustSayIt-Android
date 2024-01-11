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
    }
}

rootProject.name = "justsayit"
include(":app")
include(":authentication")
include(":authentication:authentication-presentation")
include(":core")
include(":authentication:authentication-domain")
include(":authentication:authentication-data")
include(":core:datastore")
include(":core:designsystem")
include(":user")
include(":user:user-data")
include(":user:user-domain")
include(":user:user-presentation")
include(":core:network")
