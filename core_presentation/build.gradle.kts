plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = AppConfig.compileSdkVersion

    buildFeatures {
        compose = true
    }

    defaultConfig {
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        testInstrumentationRunner = AppConfig.testInstrumentationRunner
        multiDexEnabled = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeVersion
    }
    namespace = "com.emmanuelguther.core_presentation"
}

dependencies {
    implementation(project(":commons"))
    implementation(project(":domain"))
    implementation(dependencyCoreKtx)
    implementation(dependencyLifecycleViewModel)
    dependenciesCoroutines()
    dependenciesUI()
}

