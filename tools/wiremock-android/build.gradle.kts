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

        // The minSdk could be below 21 but then we have to handle multidex by self.
        minSdkVersion(21)
        targetSdkVersion(29)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes.getByName("debug").isTestCoverageEnabled = true

    packagingOptions {

        // avoids e.g. More than one file was found with OS independent path "META-INF/ktor-client-json.kotlin_module".
        exclude("META-INF/*.kotlin_module")
    }
}

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
    // Default dependencies for unit tests
    testImplementation("junit:junit")

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

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }

//
// COPY FROM APP MODULE
//

afterEvaluate {
    // Running unit tests in release variant brings no value yet.
    // Java library modules also run only for "debug" by default.
    tasks.findByName("testReleaseUnitTest")!!.enabled = false
}

// One task to run all variant jacocoTestReport tasks.
val jacocoTestReport = tasks.register("jacocoTestReport").get()

android.libraryVariants.all {
    val variantName = name.capitalize()

    val mainVariantForAndroidTests = "debug"
    if (variantName.contains(mainVariantForAndroidTests, true)) {
        tasks.register("jacoco${variantName}UnitTestReport", de.nenick.gradle.plugins.jacoco.android.JacocoAndroidReport::class) {
            group = "Verification"
            description = "Generate Jacoco unit test coverage reports for the $variantName build."
            skipUnitTest = true // This is a on device feature and unit tests wasn't helpful yet.
            dependsOn("test${variantName}UnitTest")

            reports.html.apply {
                isEnabled = true
                destination = project.reporting.file("jacoco/test$variantName/html")
            }

            val mainSrc = sourceSets.map { it.javaDirectories }
            val execFiles = "$buildDir/jacoco/test${variantName}UnitTest.exec"
            val javaClasses = fileTree(javaCompileProvider.get().destinationDir) { exclude("**/BuildConfig.*") }
            val kotlinClasses = fileTree("$buildDir/tmp/kotlin-classes/$variantName")

            sourceDirectories.setFrom(files(mainSrc))
            classDirectories.setFrom(javaClasses, kotlinClasses)
            executionData.setFrom(execFiles)
        }.also {
            jacocoTestReport.dependsOn(it)
        }

        tasks.register("jacoco${variantName}ConnectedTestReport", JacocoReport::class) {
            group = "Verification"
            description = "Generate Jacoco connected test coverage reports for the $variantName build."
            dependsOn("connected${variantName}AndroidTest")

            reports.html.apply {
                isEnabled = true
                destination = project.reporting.file("jacoco/connected$variantName/html")
            }

            val mainSrc = sourceSets.map { it.javaDirectories }
            val execFiles = fileTree("$buildDir/outputs/code_coverage/${this@all.name}AndroidTest/connected/") { include("*-coverage.ec") }
            val javaClasses = fileTree(javaCompileProvider.get().destinationDir) { exclude("**/BuildConfig.*") }
            val kotlinClasses = fileTree("$buildDir/tmp/kotlin-classes/$variantName") {
                exclude("**/*\$\$inlined*")
            }

            sourceDirectories.setFrom(files(mainSrc))
            classDirectories.setFrom(javaClasses, kotlinClasses)
            executionData.setFrom(execFiles)
        }.also {
            jacocoTestReport.dependsOn(it)
        }
    }
}