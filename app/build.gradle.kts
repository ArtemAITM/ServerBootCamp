plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.server_bootcamp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.server_bootcamp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{
        viewBinding = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    //noinspection UseTomlInstead
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.firebase.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-analytics")
}