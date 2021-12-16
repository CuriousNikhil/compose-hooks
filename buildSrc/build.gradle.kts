
plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    kotlin("jvm") version "1.6.10"
}

repositories {
    google()
    mavenCentral()
}

gradlePlugin{
    plugins {
        register("common-plugin"){
            id = "common-plugin"
            implementationClass = "me.nikhilchaudhari.plugins.CommonPlugin"
        }
    }
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

dependencies {
    compileOnly(gradleApi())

    implementation("com.android.tools.build:gradle:7.0.3")
    implementation(kotlin("gradle-plugin", "1.5.31"))
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.6.0")
    implementation(kotlin("stdlib-jdk8"))
}
