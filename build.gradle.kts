// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.google.gms:google-services:4.4.0")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.45")
        classpath ("com.android.tools.build:gradle:8.0.0")

    }
}

plugins {
    id ("com.android.application") version "8.2.2" apply false
    id ("com.android.library" )version "8.0.0" apply false
    id ("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id ("androidx.navigation.safeargs.kotlin") version "2.5.3" apply false
    id ("com.google.dagger.hilt.android") version "2.45" apply false
}


