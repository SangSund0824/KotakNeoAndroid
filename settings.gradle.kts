pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "KotakNeoApp"
include(":app")
include(":core")
include(":core:common")
include(":core:model")
include(":core:network")
include(":core:data")
include(":core:datastore")
include(":core:ui")
include(":feature:login")
// include(":feature:marketstream")
include(":feature:trade")
include(":feature:portfolio")
include(":feature:positions")
