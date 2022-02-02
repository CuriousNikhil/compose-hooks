plugins {
    id("common-plugin")
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
}

extra.apply {
    set("PUBLISH_GROUP_ID", "<your-publish-groupid>")
    set("PUBLISH_VERSION", "<hook-version>")
    set("PUBLISH_ARTIFACT_ID", "<your-hook-library-artifact-id>")
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
