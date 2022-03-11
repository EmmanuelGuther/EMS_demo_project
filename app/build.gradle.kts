plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AppConfig.compileSdkVersion
    buildToolsVersion = AppConfig.buildToolsVersion

    defaultConfig {
        applicationId = "com.emmanuelguther.ems"
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        testInstrumentationRunner = AppConfig.testInstrumentationRunner
        multiDexEnabled = true

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeVersion
    }
    namespace = "com.emmanuelguther.ems"
}

dependencies {
    implementation(project(":features"))
    implementation(project(":core_presentation"))
    implementation(project(":di"))

    dependenciesUI()
    dependenciesHilt()

    implementation("androidx.navigation:navigation-compose:2.4.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation("androidx.navigation:navigation-testing:2.4.1")
    implementation("androidx.compose.foundation:foundation:1.1.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${Versions.composeVersion}")
    androidTestImplementation("androidx.compose.ui:ui-test:${Versions.composeVersion}")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${Versions.composeVersion}")
    androidTestImplementation("com.google.dagger:hilt-android-testing:${Versions.daggerHiltVersion}")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:${Versions.daggerHiltVersion}")
}

kapt {
    correctErrorTypes = true
}