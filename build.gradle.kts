buildscript {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath ("com.android.tools.build:gradle:7.1.2")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:${Versions.daggerHiltVersion}")
        classpath ("com.google.gms:google-services:4.3.10")
    }

}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}