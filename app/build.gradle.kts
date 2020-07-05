plugins {
    id("de.nenick.android-application-module")
    id("de.nenick.ktlint-config")
    id("jacoco")
}

android {

    compileSdkVersion(29)

    defaultConfig {
        applicationId = "de.nenick.template"

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
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("androidx.core:core-ktx:1.3.0")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")

    testImplementation("junit:junit")

    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}

afterEvaluate {
    // Running unit tests in release variant brings no value yet.
    // Java library modules also run only for "debug" by default.
    tasks.findByName("testReleaseUnitTest")!!.enabled = false
}

// One task to run all variant jacocoTestReport tasks.
val jacocoTestReport = tasks.register("jacocoTestReport").get()

android.applicationVariants.all {
    val variantName = name.capitalize()

    if (!variantName.contains("release", true)) {
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