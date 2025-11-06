pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        //firebase
        maven { url = uri("https://maven.google.com") }
        maven { url = uri("https://dl.google.com/dl/android/maven2/") }
        //네이버 지도 SDK용 저장소 추가
        maven { url = uri("https://repository.map.naver.com/archive/maven") }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        //firebase
        maven { url = uri("https://maven.google.com") }
        maven { url = uri("https://dl.google.com/dl/android/maven2/") }
        //네이버 지도 SDK용 저장소 추가
        maven { url = uri("https://repository.map.naver.com/archive/maven") }
    }
}

rootProject.name = "sohaengsung"
include(":app")
