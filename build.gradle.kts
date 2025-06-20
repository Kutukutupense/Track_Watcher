
    plugins {
        alias(libs.plugins.android.application) apply false
        alias(libs.plugins.kotlin.android) apply false
        alias(libs.plugins.kotlin.compose) apply false
        alias(libs.plugins.kotlin.kapt) apply false
        id("com.google.dagger.hilt.android") version "2.50" apply false

    }

    buildscript {
        dependencies {
            classpath("com.google.dagger:hilt-android-gradle-plugin:2.51")

        }
    }


