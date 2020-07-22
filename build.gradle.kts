import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
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
        classpath("com.android.tools.build:gradle:4.0.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")

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
            dependency("io.strikt:strikt-core:0.26.1")
        }
    }
}

tasks.getByName<JacocoMergeTask>("jacocoTestReportMerge") {
    group = "Verification"
    description = "Generate merged Jacoco coverage reports."

    subprojects {
        val android = extensions.findByType<BaseAppModuleExtension>()
        val kotlin = extensions.findByType<org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension>()

        if (android != null) {
            val variant = extensions.getByType<BaseAppModuleExtension>().applicationVariants.findLast { it.name == "debug" }!!
            val sourceFiles = files(variant.sourceSets.map { it.javaDirectories })
            additionalSourceDirs(sourceFiles)

            tasks.getByName("jacocoDebugConnectedTestReport").run {
                executionData(fileTree("${buildDir}/outputs/code_coverage/debugAndroidTest/connected/*"))
            }

            tasks.matching { it.extensions.findByType<JacocoTaskExtension>() != null }.configureEach {
                if (!name.contains("release", true)) {
                    executionData(this)
                }
            }
            additionalClassDirs(fileTree("${buildDir}/tmp/kotlin-classes/debug"))
        } else if (kotlin != null) {
            additionalSourceDirs(kotlin.sourceSets.findByName("main")!!.kotlin.sourceDirectories)

            tasks.matching { it.extensions.findByType<JacocoTaskExtension>() != null }.configureEach {
                executionData(this)
            }
            additionalClassDirs(fileTree("${buildDir}/classes/kotlin/main") {
                exclude("**/*\$\$inlined*")
            })
        } else {
            if(!projectDir.listFiles { file, _ -> !file.isDirectory }.isNullOrEmpty()) {
                throw java.lang.IllegalStateException("found no android nor kotlin extension but module looks like it has project files")
            }
        }
    }
}