import org.jetbrains.kotlin.gradle.internal.kapt.incremental.UnknownSnapshot

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false

    id("com.android.library") version "7.4.2" apply false
//    id("io.realm.kotlin") version "1.6.1" apply false
//    id("com.google.dagger.hilt.android") version "2.44" apply false
}

buildscript {

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.4.2")
    }
}