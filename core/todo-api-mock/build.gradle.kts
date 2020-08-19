plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("de.nenick.ktlint-android-config")
    id("jacoco")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

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

// One task to run all variant jacocoTestReport tasks.
val jacocoTestReport = tasks.register("jacocoTestReport").get()

android.libraryVariants.all {
    val variantName = name.capitalize()

    val mainVariantForAndroidTests = "debug"
    if (variantName.contains(mainVariantForAndroidTests, true)) {
        tasks.register("jacoco${variantName}UnitTestReport", de.nenick.gradle.plugins.jacoco.android.JacocoAndroidReport::class) {
            group = "Verification"
            description = "Generate Jacoco unit test coverage reports for the $variantName build."
            skipUnitTest = true
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

        tasks.register("jacoco${variantName}ConnectedTestReport", de.nenick.gradle.plugins.jacoco.android.JacocoAndroidReport::class) {
            group = "Verification"
            description = "Generate Jacoco connected test coverage reports for the $variantName build."
            skipAndroidTest = true
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