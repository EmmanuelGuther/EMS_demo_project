dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "EMS"
include (":app")
include (":commons")
include (":data")
include (":features")
include (":di")
include (":domain")
include (":core_presentation")
