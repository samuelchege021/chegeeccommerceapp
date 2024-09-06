plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.samuel.cheges"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.samuel.cheges"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}  `````````````````````````````122"
        }
    }
}

dependencies {

    // Firebase dependencies

    // Firebase core library
    implementation (libs.firebase.core)// Check for the latest version

    // Firestore
    implementation (libs.com.google.firebase.firebase.firestore)// Check for the latest version

    implementation(platform(libs.firebase.bom.v3200))
    implementation(libs.google.firebase.firestore)
    implementation(libs.google.firebase.auth)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage.ktx)

    // Jetpack Compose


    val composeBom = platform("androidx.compose:compose-bom:2024.06.00")
    implementation(composeBom)
    testImplementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(platform(libs.androidx.compose.bom.v202410)) // Use the BOM for Compose
    implementation(libs.androidx.ui.v140)
    implementation(libs.androidx.foundation.v140)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.material)
    implementation (libs.material)
    implementation (libs.androidx.material3.v100beta03)// Use the correct Material3 library
    implementation(libs.androidx.core.ktx.v1120)
    implementation(libs.androidx.activity.compose.v170)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.compose.v260)
    implementation(libs.coil.compose.v210)
    implementation(libs.androidx.material3.android)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)
    androidTestImplementation(libs.ui.test.junit4)

    // Debugging dependencies
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}



