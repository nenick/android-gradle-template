plugins {
    id("de.nenick.android-application-module")
    id("de.nenick.ktlint-config")
    id("jacoco")
}

android {

    compileSdkVersion(29)

    defaultConfig {
        applicationId = "de.nenick.template"

        minSdkVersion(21) // The minSdk could be below 21 but then we have to handle multidex by self.
        targetSdkVersion(29)

        versionCode = 1 // TODO use the timestamp / 10 algorithm which avoids conflicts for the next few hundreds years.
        versionName = "0.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            // Default generated proguard configuration, see also https://developer.android.com/studio/build/shrink-code
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            // TODO move this to onDeviceServer buildType because we use only that for automatic testing.
            // We have it still in debug to support coverage report checks, which do report when coverage is not enabled.
            isTestCoverageEnabled = true
        }

        //================================================================================ #onDeviceServer
        // Support for providing an on device mock server for stable demos and testing.
        //
        // Change the testBuildType if you want to target a different server for automated
        // testing e.g. a local started WireMock instance.
        //
        // Requirements: We want to support on device mock server without effecting to
        // many variants because current it needs a lot of customizations which reduce
        // realism (e.g. include legacy http client). It should be possible to use all
        // app variants with the on device server for demo and testing. Also it shouldn't
        // introduce to much project setup complexity. That's why we prefer buildType
        // instead of product flavor.
        //
        // See also
        // * Request to support multiple buildTypes https://issuetracker.google.com/issues/154260222
        // * Missing hint why test classes aren't resolvable https://issuetracker.google.com/issues/148451831
        //
        create("onDeviceServer") {
            // Make sure that new developers will have less issues when they want to run
            // the androidTest the first time after initial cloning.
            isDefault = true
            // Basically it should have the same setup as the debug variant.
            initWith(getByName("debug"))
            matchingFallbacks = listOf("debug")
        }
    }

    // Search for #onDeviceServer
    testBuildType = "onDeviceServer"

    lintOptions {
        // We handle each reported lint issue as an error which sometimes detects real important issues.
        isWarningsAsErrors = true
        isAbortOnError = false
    }

    packagingOptions {
        // Avoids issues like: More than one file was found with OS independent path "META-INF/ktor-client-json.kotlin_module".
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {
    // With Kotlin DSL we have to "access" first the dependency configuration  target for different variants.
    // The variable naming will be checked by the compiler that this variant does really exists.
    val onDeviceServerImplementation by configurations

    implementation(project(":core:todo-api"))
    onDeviceServerImplementation(project(":core:todo-api-mock"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.koin:koin-android")

    implementation("androidx.core:core-ktx")
    implementation("androidx.activity:activity-ktx")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx")
    implementation("androidx.appcompat:appcompat")
    implementation("androidx.constraintlayout:constraintlayout")

    testImplementation("junit:junit")

    androidTestImplementation("androidx.test.ext:junit")
    androidTestImplementation("androidx.test.espresso:espresso-core")
    androidTestImplementation("androidx.test:rules:1.2.0")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }

// One task to run all variant jacocoTestReport tasks.
val jacocoTestReport = tasks.register("jacocoTestReport").get()

android.applicationVariants.all {
    val variantName = name.capitalize()

    val mainVariantForAndroidTests = "debug"
    if (variantName.contains(mainVariantForAndroidTests, true)) {
        tasks.register("jacoco${variantName}UnitTestReport", JacocoReport::class) {
            group = "Verification"
            description = "Generate Jacoco unit test coverage reports for the $variantName build."
            dependsOn("test${variantName}UnitTest")

            reports.html.apply {
                isEnabled = true
                destination = project.reporting.file("jacoco/test$variantName/html")
            }

            val mainSrc = sourceSets.map { it.javaDirectories }
            val execFiles = "$buildDir/jacoco/test${variantName}UnitTest.exec"
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