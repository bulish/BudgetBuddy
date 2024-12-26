import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.services)
    kotlin("kapt")
    alias(libs.plugins.com.google.dagger.hilt.android)
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").reader())

var versionMajor = 1
var versionMinor = 0
var versionPatch = 1

android {
    namespace = "com.example.budgetbuddy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.budgetbuddy"
        minSdk = 28
        targetSdk = 34

        multiDexEnabled = true

        versionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch
        versionName = "${versionMajor}.${versionMinor}.${versionPatch}"

        // testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.example.budgetbuddy.HiltTestRunner"
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
            buildConfigField("String", "MAP_API_KEY", properties.getProperty("google_maps_api_key"))
            buildConfigField("String", "BASE_URL", properties.getProperty("baseurldevel"))
        }
        debug {
            buildConfigField("String", "MAP_API_KEY", properties.getProperty("google_maps_api_key"))
            buildConfigField("String", "BASE_URL", properties.getProperty("baseurlproduction"))
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "META-INF/*"
        }
    }
    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
            it.jvmArgs?.add("-javaagent:${rootDir}/libs/mockk-agent.jar")
        }

        packagingOptions {
            jniLibs {
                useLegacyPackaging = true
            }
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    implementation(libs.navigation.compose)

    implementation(libs.room.ktx)
    implementation(libs.room.viewmodel)
    implementation(libs.room.lifecycle)
    implementation(libs.room.runtime)

    implementation(libs.hilt)
    implementation(libs.hilt.compose)
    kapt(libs.hilt.compiler.kapt)

    implementation(libs.moshi)
    implementation(libs.moshi.codegen)
    kapt(libs.moshi.kapt)
    implementation(libs.moshi.kotlin)
    kapt(libs.room.compiler.kapt)

    implementation(libs.datastore)

    implementation(libs.image.cropper)
    implementation(libs.accompanist)

    implementation(libs.lifecycle)
    implementation(libs.googlemap)
    implementation(libs.googlemap.compose)
    implementation(libs.googlemap.foundation)
    implementation(libs.googlemap.utils)
    implementation(libs.googlemap.widgets)
    implementation(libs.googlemap.compose.utils)

    implementation(libs.places)
    implementation(libs.glide)
    implementation(libs.lottie)

    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.retrofit.okhtt3)

    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)

    // required if you want to use Mockito for unit tests
    testImplementation(libs.mockito.core)
    // required if you want to use Mockito for Android tests
    androidTestImplementation(libs.mockito.android)

    testImplementation("io.mockk:mockk:1.13.5")
    androidTestImplementation("io.mockk:mockk-android:1.13.5")
    testImplementation("io.mockk:mockk-agent:1.13.5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    implementation(libs.tracing)
    implementation("com.google.firebase:firebase-auth:21.0.7")
    testImplementation("org.mockito:mockito-core:4.0.0")
    androidTestImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.11.0")

    implementation(libs.text.recognition)
    implementation(libs.camera.core)
    implementation(libs.camera.view)
    implementation(libs.camera.camera2)
    implementation(libs.camera.lifecyvle)
    implementation(libs.camera.extensions)
    implementation(libs.camera.video)
    implementation(libs.guava)

    implementation(libs.splashscreen)

}