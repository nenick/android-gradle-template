plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("de.nenick.ktlint-android-config")
    id("de.nenick.jacoco-android-config")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes.getByName("debug").isTestCoverageEnabled = true

    packagingOptions {
        // Avoid conflicts like "More than one file was found with OS independent path ..."
        exclude("META-INF/*")
    }
}

dependencies {
    implementation(project(":core:todo-api"))
    implementation(project(":tools:wiremock-android"))
    implementation(project(":tools:wiremock-kotlindsl"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("io.ktor:ktor-client-android")
    implementation("org.koin:koin-android")
    implementation("androidx.test.espresso:espresso-idling-resource:3.2.0")
}

//
// COPY FROM APP MODULE
//

afterEvaluate {
    // Running unit tests in release variant brings no value yet.
    // Java library modules also run only for "debug" by default.
    tasks.findByName("testReleaseUnitTest")!!.enabled = false
}

jacocoAndroid {
    androidUnitTests.skipCoverageReport = true
    connectedAndroidTests.skipCoverageReport = true
}