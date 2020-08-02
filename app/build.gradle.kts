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
        create("onDeviceServer") {
            initWith(getByName("debug"))
            matchingFallbacks = listOf("debug")
        }
    }

    lintOptions {
        isWarningsAsErrors = true
        isAbortOnError = false
    }

    packagingOptions {
        // avoids e.g. More than one file was found with OS independent path "META-INF/ktor-client-json.kotlin_module".
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {
    val onDeviceServerImplementation by configurations

    implementation(project(":core-data-network"))
    onDeviceServerImplementation(project(":core-data-network-mock"))

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
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }

afterEvaluate {
    // Running unit tests in release variant brings no value yet.
    // Java library modules also run only for "debug" by default.
    tasks.findByName("testReleaseUnitTest")!!.enabled = false
}

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
            val kotlinClasses = fileTree("$buildDir/tmp/kotlin-classes/$variantName")

            sourceDirectories.setFrom(files(mainSrc))
            classDirectories.setFrom(javaClasses, kotlinClasses)
            executionData.setFrom(execFiles)
        }.also {
            jacocoTestReport.dependsOn(it)
        }
    }
}