pluginManagement {
    repositories {
        google {
            plugins{
                id("androidx.navigation.safeargs.kotlin") version "2.8.5"
                id("com.google.devtools.ksp") version "SETA_VERSAO_KSP_AQUI" apply false
            }
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

rootProject.name = "ImparkApk"
include(":app")

