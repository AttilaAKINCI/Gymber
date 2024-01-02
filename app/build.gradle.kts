plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("de.mannodermaus.android-junit5") version "1.10.0.0"
}

repositories {
    google()
    mavenCentral()
}

android {
    namespace = "com.akinci.gymber"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.akinci.gymber"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.akinci.gymber.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    lint {
        abortOnError = true
        ignoreWarnings = false
        warningsAsErrors = true
        checkReleaseBuilds = false
        baseline = file("lint-baseline.xml")
    }
    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests {
            isReturnDefaultValues = true
            all {
                it.useJUnitPlatform()
            }
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val lifecycleVersion = "2.6.2"
    val composeBomVersion = "2023.10.01"
    val jUnit5Version = "5.10.1"
    val hiltVersion = "2.50"
    val composeDestinationsVersion = "1.9.56"
    val ktorVersion = "2.3.7"
    val coilVersion = "2.5.0"
    val lottieVersion = "6.2.0"
    val timberVersion = "5.0.1"
    val coroutinesVersion = "1.7.3"

    // CORE
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.core:core-splashscreen:1.0.1")

    // JETBRAINS CORE
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.7")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")

    // LOCATION
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // IMAGE LOADING
    implementation("io.coil-kt:coil-compose:$coilVersion")

    // LOTTIE ANIMATIONS
    implementation("com.airbnb.android:lottie-compose:$lottieVersion")

    // NETWORK
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    implementation("io.ktor:ktor-client-mock:$ktorVersion")

    // LIFECYCLE
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion")

    // COMPOSE
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.material3:material3")

    // NAVIGATION
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("io.github.raamcosta.compose-destinations:core:$composeDestinationsVersion")
    implementation("io.github.raamcosta.compose-destinations:animations-core:$composeDestinationsVersion")
    ksp("io.github.raamcosta.compose-destinations:ksp:$composeDestinationsVersion")

    // DEPENDENCY INJECTION - HILT
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    ksp("com.google.dagger:hilt-android-compiler:$hiltVersion")

    // LOGGING
    implementation("com.jakewharton.timber:timber:$timberVersion")

    // UNIT TESTING
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnit5Version")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnit5Version")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$jUnit5Version")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("app.cash.turbine:turbine:1.0.0")
}
