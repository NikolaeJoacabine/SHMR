plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.nikol.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "TOKEN", "\"gHeljRylS1BWmVXaqUPHGSVf\"")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.okhttp)
    api(libs.okhttp.logging)

    api(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    implementation(libs.dagger)
    ksp(libs.dagger.compiler)

    implementation(project(":domain"))
    implementation(libs.androidx.work.runtime.ktx)

    implementation(libs.androidx.datastore)
    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
}
