import Versions.coroutinesVersion
import Versions.gson
import Versions.okhttp3LoggingInterceptor
import Versions.retrofitVersion
import org.gradle.api.artifacts.dsl.DependencyHandler

object Versions {
    const val composeVersion = "1.1.1"
    const val daggerHiltVersion = "2.40.4"
    const val coroutinesVersion = "1.6.0"
    const val kotlinVersion = "1.6.10"
    const val retrofitVersion = "2.9.0"
    const val okhttp3LoggingInterceptor = "4.9.3"
    const val gson = "2.9.0"
}

object AppConfig {
    const val compileSdkVersion = 31
    const val minSdkVersion = 24
    const val buildToolsVersion = "30.0.3"
    const val targetSdkVersion = 31
    const val versionName = "0.1"
    const val versionCode = 1
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

const val dependencyCoreKtx = "androidx.core:core-ktx:1.7.0"
const val dependencyLifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0"

fun DependencyHandler.dependenciesNetwork() {
    add("implementation", "com.squareup.retrofit2:retrofit:${retrofitVersion}")
    add("implementation", "com.squareup.retrofit2:converter-gson:${retrofitVersion}")
    add("implementation", "com.squareup.okhttp3:logging-interceptor:${okhttp3LoggingInterceptor}")
    add("implementation", "com.google.code.gson:gson:${gson}")
}

fun DependencyHandler.dependenciesCoroutines() {
    add("implementation", "org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutinesVersion}")
    add("implementation", "org.jetbrains.kotlinx:kotlinx-coroutines-android:${coroutinesVersion}")
}

fun DependencyHandler.dependenciesHilt() {
    add("implementation", "com.google.dagger:hilt-android:${Versions.daggerHiltVersion}")
    add("kapt", "com.google.dagger:hilt-compiler:${Versions.daggerHiltVersion}")
}

fun DependencyHandler.dependenciesUI() {
    add("implementation", "androidx.compose.ui:ui:${Versions.composeVersion}")
    add("implementation", "androidx.compose.material:material:${Versions.composeVersion}")
    add("implementation", "androidx.compose.ui:ui-tooling:${Versions.composeVersion}")
    add("implementation", "androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    add("implementation", "androidx.activity:activity-compose:1.4.0")
}


