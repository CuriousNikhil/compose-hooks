package me.nikhilchaudhari.plugins

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class CommonPlugin : Plugin<Project>{

    private val Project.android: BaseExtension
    get() = extensions.findByName("android") as? BaseExtension ?: error("Not an Android module $name")

    override fun apply(target: Project) {
        with(target){
            applyPlugins()
            androidConfig()
            dependenciesConfig()
        }
    }

    private fun Project.applyPlugins(){
        plugins.run{
            apply("com.android.library")
            apply("kotlin-android")
        }
    }

    private fun Project.androidConfig(){
        android.run {
            compileSdkVersion(31)

            defaultConfig {
                minSdk = 21
                targetSdk = 31

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            buildTypes {
                getByName("release") {
                    isMinifyEnabled = false
                }
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
            composeOptions {
                kotlinCompilerExtensionVersion = Versions.compose
            }
            packagingOptions.exclude("META-INF/main.kotlin_module")
        }
    }

    private fun Project.dependenciesConfig(){
        dependencies {

        }
    }

}
