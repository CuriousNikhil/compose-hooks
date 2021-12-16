plugins {
    id("common-plugin")
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
}

extra.apply {
    set("PUBLISH_GROUP_ID", "me.nikhilchaudhari")
    set("PUBLISH_VERSION", "1.0.0-alpha1.2")
    set("PUBLISH_ARTIFACT_ID", "compose-usefetch")
}

apply(from = "${rootProject.projectDir}/scripts/publish-module.gradle")

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
