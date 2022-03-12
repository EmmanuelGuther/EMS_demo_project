plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = AppConfig.compileSdkVersion
    namespace = "com.emmanuelguther.data"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":commons"))
    dependenciesCoroutines()
    dependenciesHilt()
    dependenciesNetwork()
}