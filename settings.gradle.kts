dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "UseFetch"

val modules = listOf(
    ":app",
    ":usefetch",
    ":useReducer",
    ":useNetworkState",
    ":compose-hook-template"
)

include(*(modules.toTypedArray()))
