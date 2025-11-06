pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        //네이버 지도 SDK용 저장소 추가
        maven { url = uri("https://repository.map.naver.com/archive/maven") }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        //네이버 지도 SDK용 저장소 추가
        maven { url = uri("https://repository.map.naver.com/archive/maven") }
    }
}

rootProject.name = "sohaengsung"
include(":app")
