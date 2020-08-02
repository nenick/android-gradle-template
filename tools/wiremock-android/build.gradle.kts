plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("de.nenick.ktlint-android-config")
}

android {
    compileSdkVersion(29)

    defaultConfig {

        // The minSdk could be below 21 but then we have to handle multidex by self.
        minSdkVersion(21)
        targetSdkVersion(29)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    packagingOptions {

        // avoids e.g. More than one file was found with OS independent path "META-INF/ktor-client-json.kotlin_module".
        exclude("META-INF/*.kotlin_module")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }

dependencies {

    // --------------------------------------------------------------------------------
    // Default Android and Kotlin dependencies.
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android")
    implementation("org.koin:koin-android")
    implementation("androidx.core:core-ktx")

    // --------------------------------------------------------------------------------
    // Dependencies to enable WireMock.
    api("com.github.tomakehurst:wiremock") {
        // Exclude origin allows us to use the Android version of Apache httpclient instead
        exclude("org.apache.httpcomponents", "httpclient")
    }

    // Android compatible version of Apache httpclient for WireMock.
    implementation("org.apache.httpcomponents:httpclient-android:4.3.5.1")

    // --------------------------------------------------------------------------------
    // Default dependencies for Android tests
    androidTestImplementation("androidx.test.ext:junit")
    androidTestImplementation("androidx.test:rules:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core")
    androidTestImplementation("io.strikt:strikt-core")

    // --------------------------------------------------------------------------------
    // Dependencies to test communication with WireMock server.
    androidTestImplementation("io.ktor:ktor-client-android")
    androidTestImplementation("io.ktor:ktor-client-json")
    androidTestImplementation("io.ktor:ktor-client-gson")
}