pluginManagement {
    repositories {
        google() // Android plugin burada bulunur
        gradlePluginPortal() // Kotlin plugin burada bulunur
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "qrScannerSdk"
include(":qrScannerSdk")
