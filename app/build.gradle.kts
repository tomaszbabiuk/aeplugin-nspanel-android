plugins {
    kotlin("kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "ae.geekhome.panel"
    compileSdk = 34

    defaultConfig {
        applicationId = "ae.geekhome.panel"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
    buildFeatures { compose = true }
    composeOptions { kotlinCompilerExtensionVersion = "1.4.3" }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/legal/{LICENSE,NOTICE.md}"
            excludes +=
                "/META-INF/legal/3rd-party/{CDDL+GPL-1.1.txt,cc0-legalcode.html,BSD-3-Clause-LICENSE.txt,APACHE-LICENSE-2.0.txt,MIT-license.html}"
        }
    }
}

dependencies {

    //automate-everything
    implementation("eu.automateeverything:data:0.1")
    implementation("eu.automateeverything:aeplugin-tablets:0.1.0")

    // kotlin
    implementation("androidx.core:core-ktx:1.12.0")

    // android
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // jetpack compose
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // californium
    implementation("org.eclipse.californium:californium-core:3.8.0")

    // hilt
    implementation("com.google.dagger:hilt-android:2.46.1")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // compose navigation
    implementation("androidx.navigation:navigation-compose:2.7.3")

    // slf4j
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("org.slf4j:slf4j-android:1.7.36")

    // cbor
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-cbor:1.6.0")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

kapt { correctErrorTypes = true }
