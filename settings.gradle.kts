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

rootProject.name = "Notes"
include(":app")
include(":data:notes:api")
include(":data:notes:room")
include(":utils")
include(":feature:note-details")
include(":core:note-presentation")
include(":core:presentation-domain-mapper")
include(":core:dialog")
include(":core:presentation")
