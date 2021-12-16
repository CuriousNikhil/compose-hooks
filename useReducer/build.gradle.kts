
plugins {
    id("common-plugin")
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
}

android {
    buildFeatures {
        compose = true
    }
}

dependencies {
    androidxDependencies()
    composeDependencies()
    testDependencies()
}
