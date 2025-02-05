import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "pl.kormateusz.weather"
    compileSdk = 35

    val secretPropertiesFile = rootProject.file("secret.properties")
    val secretProperties = Properties()
    secretProperties.load(secretPropertiesFile.inputStream())

    defaultConfig {
        applicationId = "pl.kormateusz.weather"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "API_KEY", secretProperties.getProperty("API_KEY"))
    }
    buildFeatures {
        buildConfig = true
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
    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            dimension = "environment"
            buildConfigField(
                "String",
                "API_URL",
                "\"https://dataservice.accuweather.com/\""
            ) // for some reason apidev.accuweather.com doesn't work
            applicationIdSuffix = ".dev"
        }
        create("prod") {
            dimension = "environment"
            buildConfigField(
                "String",
                "API_URL",
                "\"https://dataservice.accuweather.com/\""
            ) // for some reason api.accuweather.com doesn't work
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.navigation)

    implementation(libs.koin)
    implementation(libs.koin.android)
    implementation(libs.koin.android.compat)
    implementation(libs.koin.android.compose)

    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    testImplementation(libs.junit)
    testImplementation(libs.test.mockito)
    testImplementation(libs.test.mockito.inline)
    testImplementation(libs.test.mockito.kotlin)
    testImplementation(libs.test.coroutines)
    testImplementation(libs.test.coroutines.turbine)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}