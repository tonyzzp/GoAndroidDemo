import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val compose_version: String by rootProject.extra

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "me.izzp.godemo"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
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
        kotlinCompilerExtensionVersion = compose_version
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

tasks.getByName("preBuild").doFirst {
    val p = rootProject.file("../../build/godemo.aar")
    if (!p.exists()) {
        error("godemo.aar does not exist, call build_aar.bat first")
    }
}

afterEvaluate {
    android.applicationVariants.forEach { variant ->
        variant.outputs.firstOrNull()?.also {
            val src = it.outputFile
            variant.assembleProvider.get().doLast {
                println(src)
                val dst =
                    rootProject.file("../../BUILD_APKS/${variant.name}-${variant.buildType.name}-${variant.versionName}.apk")
                src.copyTo(dst, true)
            }
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.material3:material3:1.0.0-alpha15")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.5.1")
    implementation(files(rootProject.file("../../build/godemo.aar").absolutePath))
    debugImplementation("androidx.compose.ui:ui-tooling:$compose_version")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_version")
}