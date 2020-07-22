plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("de.nenick.ktlint-config")
}

android {

    compileSdkVersion(29)

    defaultConfig {

        minSdkVersion(14)
        targetSdkVersion(29)

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isTestCoverageEnabled = true
        }
    }

    lintOptions {
        isWarningsAsErrors = true
        isAbortOnError = false
    }

    packagingOptions {
        // avoid general conflicts e.g. More than one file was found with OS independent path "META-INF/ktor-client-json.kotlin_module".
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    // --------------------------------------------------------------------------------
    // Dependencies for android tests

    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
    androidTestImplementation("io.strikt:strikt-core")

    // --------------------------------------------------------------------------------
    // Dependencies for jvm tests

    testImplementation("junit:junit:4.13")
    testImplementation("io.strikt:strikt-core")

    // --------------------------------------------------------------------------------
    // Dependencies to test WireMock communication.

    testImplementation("io.ktor:ktor-client-android:1.3.2")
    testImplementation("io.ktor:ktor-client-json:1.3.2")
    testImplementation("io.ktor:ktor-client-gson:1.3.2")
    androidTestImplementation("io.ktor:ktor-client-android:1.3.2")
    androidTestImplementation("io.ktor:ktor-client-json:1.3.2")
    androidTestImplementation("io.ktor:ktor-client-gson:1.3.2")

    // --------------------------------------------------------------------------------
    // Dependencies to enable WireMock.

    implementation("com.github.tomakehurst:wiremock:2.26.3") {
        // Allows us to use the Android version of Apache httpclient instead
        exclude("org.apache.httpcomponents", "httpclient")
    }

    // Android compatible version of Apache httpclient for WireMock.
    implementation("org.apache.httpcomponents:httpclient-android:4.3.5.1")
}