plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "com.app.kiosk"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.app.kiosk"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    hilt {
        enableAggregatingTask = false
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // gson
    implementation(libs.gson)

    // hilt dependency
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // view model
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.runtime)

    // room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    // ksp(libs.room.complier)
    ksp("androidx.room:room-compiler:2.4.3")


    // glide image load
    implementation(libs.bumptech.glide)
}