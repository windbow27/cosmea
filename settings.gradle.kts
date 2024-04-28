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

rootProject.name = "cosmea"
include(":app")
include(":feature")
include(":core")
include(":core:ui")
include(":core:designsystem")
include(":core:data")
include(":feature:profile")
include(":feature:servers")
include(":feature:messages")
include(":feature:notifications")
include(":feature:conversation")
include(":core:model")
