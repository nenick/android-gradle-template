plugins {
    id("com.android.library")
    id("kotlin-android")
    id("de.nenick.jacoco-android-config")
    id("de.nenick.ktlint-android-config")
}

android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(21)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }
    }
    packagingOptions {
        // Avoid conflicts like "More than one file was found with OS independent path ..."
        exclude("META-INF/*")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    implementation("org.jetbrains.kotlinx:atomicfu:0.14.4")
    implementation("androidx.test.ext:junit")
    implementation("androidx.test.espresso:espresso-core")
    implementation("io.mockk:mockk-android")

    androidTestImplementation("androidx.lifecycle:lifecycle-viewmodel-ktx")
    androidTestImplementation("io.strikt:strikt-core")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }

jacocoAndroid {
    androidUnitTests.skipCoverageReport = true
}