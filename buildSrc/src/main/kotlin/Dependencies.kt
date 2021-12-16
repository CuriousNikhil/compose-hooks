import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.extra

object Versions{
    val compose = "1.0.5"
    val androidx = "1.4.0"
}



fun DependencyHandlerScope.composeDependencies(){
    "implementation"("androidx.compose.ui:ui:${Versions.compose}")
    "implementation"("androidx.compose.material:material:${Versions.compose}")
    "implementation"("androidx.compose.ui:ui-tooling-preview:${Versions.compose}")
    "debugImplementation"("androidx.compose.ui:ui-tooling:${Versions.compose}")
    "androidTestImplementation"("androidx.compose.ui:ui-test-junit4:${Versions.compose}")
}

fun DependencyHandlerScope.testDependencies(){
    "testImplementation"("junit:junit:4.+")
    "androidTestImplementation"("androidx.test.ext:junit:1.1.3")
    "androidTestImplementation"("androidx.test.espresso:espresso-core:3.4.0")
}

fun DependencyHandlerScope.androidxDependencies(){
    "implementation"("androidx.core:core-ktx:1.7.0")
    "implementation"("androidx.appcompat:appcompat:${Versions.androidx}")
    "implementation"("com.google.android.material:material:${Versions.androidx}")
    "implementation"("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    "implementation"("androidx.activity:activity-compose:1.3.1")
}
