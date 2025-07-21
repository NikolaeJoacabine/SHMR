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

rootProject.name = "yandex school"
include(":app")
include(":domain")
include(":data")
include(":core:navigation")
include(":core:di")
include(":features:transaction")
include(":core:ui")
include(":features:account")
include(":features:articles")
include(":features:settings")
include(":core:chartsUi")
