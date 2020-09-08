import de.nenick.gradle.plugins.jacoco.merge.JacocoMergeTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("de.nenick.android-project")
    id("de.nenick.jacoco-merge")
    id("de.nenick.check-tasks")
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
}

buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.1.0-rc02")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.0")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    apply(plugin = "io.spring.dependency-management")

    repositories {
        google()
        jcenter()
    }

    // Share common library versions, sub modules don't need to specify the version anymore.
    dependencyManagement {
        dependencies {
            dependency("junit:junit:4.13")
            dependency("io.strikt:strikt-core:0.27.0")
            dependency("io.mockk:mockk-android:1.10.0")
            dependency("com.github.tomakehurst:wiremock:2.26.3")
            dependency("com.github.tomakehurst:wiremock-jre8:2.26.3")

            dependency("org.koin:koin-core:2.1.6")
            dependency("org.koin:koin-android:2.1.6")
            dependency("org.koin:koin-test:2.1.6")

            dependency("org.jetbrains.kotlin:kotlin-stdlib:1.4.0")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.9")

            dependency("androidx.core:core-ktx:1.3.1")
            dependency("androidx.activity:activity-ktx:1.1.0")
            dependency("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
            dependency("androidx.appcompat:appcompat:1.2.0")
            dependency("androidx.constraintlayout:constraintlayout:2.0.1")

            dependency("androidx.test.ext:junit:1.1.2")
            dependency("androidx.test:rules:1.3.0")
            dependency("androidx.test.espresso:espresso-core:3.3.0")
            dependency("androidx.test.espresso:espresso-idling-resource:3.3.0")

            dependency("io.ktor:ktor-client-android:1.3.2")
            dependency("io.ktor:ktor-client-json:1.3.2")
            dependency("io.ktor:ktor-client-gson:1.3.2")
        }
    }

    // Running unit tests in release variant brings no value yet.
    // Java library modules also run only for "debug" by default.
    tasks.findByName("testReleaseUnitTest")?.enabled = false
}

tasks.getByName<JacocoMergeTask>("jacocoTestReportMerge") {
    group = "Verification"
    description = "Generate merged Jacoco coverage reports."

    subprojects {
        val androidApp = extensions.findByType<com.android.build.gradle.AppExtension>()
        val androidLibrary = extensions.findByType<com.android.build.gradle.LibraryExtension>()
        val androidTest = extensions.findByType<com.android.build.gradle.TestExtension>()
        val kotlin = extensions.findByType<org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension>()

        if (androidApp != null || androidLibrary != null || androidTest != null) {

            val variantName = if( name == "app" || name == "app-tests-on-device-server") {
                "onDeviceServer"
            } else {
                "debug"
            }

            val variant =
                extensions.findByType<com.android.build.gradle.AppExtension>()?.applicationVariants?.findLast { it.name == variantName }
                    ?: extensions.findByType<com.android.build.gradle.LibraryExtension>()?.libraryVariants?.findLast { it.name == variantName }
                    ?: extensions.getByType<com.android.build.gradle.TestExtension>().applicationVariants.findLast { it.name == variantName }!!
            val sourceFiles = files(variant.sourceSets.map { it.javaDirectories })
            if(androidTest == null)
                additionalSourceDirs(sourceFiles)

            tasks.getByName("jacoco${variantName.capitalize()}ConnectedTestReport").run {
                executionData(fileTree("${buildDir}/outputs/code_coverage/${variantName}AndroidTest/connected/*"))
            }

            val jacocoAndroid = extensions.getByType(de.nenick.gradle.plugins.jacoco.android.JacocoAndroidExtension::class)
            tasks.matching {
                val hasJacoco = it is JacocoReport
                val skipUnitTests =
                    it is de.nenick.gradle.plugins.jacoco.android.JacocoAndroidUnitTestReport && jacocoAndroid.androidUnitTests.skipCoverageReport
                val skipAndroidTests =
                    it is de.nenick.gradle.plugins.jacoco.android.JacocoConnectedAndroidTestReport && jacocoAndroid.connectedAndroidTests.skipCoverageReport

                if (it is de.nenick.gradle.plugins.jacoco.android.JacocoConnectedAndroidTestReport) {
                    androidApp?.let { extension ->
                        if (!extension.buildTypes.getByName(variantName).isTestCoverageEnabled)
                            throw IllegalStateException("test coverage has to be enabled for $name ${it.name}")
                    }
                    androidLibrary?.let { extension ->
                        if (!extension.buildTypes.getByName(variantName).isTestCoverageEnabled)
                            throw IllegalStateException("test coverage has to be enabled for $name ${it.name}")
                    }
                }

                hasJacoco && !skipUnitTests && !skipAndroidTests
            }.configureEach {
                if (!name.contains("release", true)) {
                    executionData((this as JacocoReport).executionData)
                }
            }
            if(androidTest == null)
                additionalClassDirs(fileTree("${buildDir}/tmp/kotlin-classes/${variantName}") {
                    exclude("**/*\$\$inlined*")
                })

        } else if (kotlin != null) {
            additionalSourceDirs(kotlin.sourceSets.findByName("main")!!.kotlin.sourceDirectories)

            tasks.matching { it.extensions.findByType<JacocoTaskExtension>() != null }.configureEach {
                executionData(this)
            }
            additionalClassDirs(fileTree("${buildDir}/classes/kotlin/main") {
                exclude("**/*\$\$inlined*")
            })
        } else {
            if (!projectDir.listFiles { file, _ -> !file.isDirectory }.isNullOrEmpty()) {
                throw java.lang.IllegalStateException("found no android nor kotlin extension but module looks like it has project files")
            }
        }
    }
}