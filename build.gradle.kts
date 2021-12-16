// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    extra.apply {
        set("compose_version", "1.0.5")
    }
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.6.0")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

plugins{
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}

apply(from = "${rootProject.projectDir}/scripts/publish-root.gradle")
