plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.assignment_writer"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.assignment_writer"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {


    implementation("com.google.ai.client.generativeai:generativeai:0.9.0")
    implementation ("com.google.guava:guava:31.1-jre")
    implementation("org.reactivestreams:reactive-streams:1.0.4")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.facebook.shimmer:shimmer:0.5.0")
    implementation ("com.google.firebase:firebase-auth:22.3.1")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}