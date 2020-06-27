import com.android.build.gradle.internal.dsl.BaseAppModuleExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("nenick-android-project")
    id("jacoco")
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

tasks.register("jacocoTestReportMerge", JacocoReport::class) {
    group = "Verification"
    description = "Generate merged Jacoco coverage reports."

    reports.html.apply {
        isEnabled = true
        destination = project.reporting.file("jacoco/merged/html")
    }
    reports.xml.apply {
        isEnabled = true
        destination = project.reporting.file("jacoco/merged/jacocoTestReport.xml")
    }

    subprojects {
        val android = extensions.findByType<BaseAppModuleExtension>()
        val kotlin = extensions.findByType<org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension>()


        val kotlinClasses = if (android != null) {
            val variant = extensions.getByType<BaseAppModuleExtension>().applicationVariants.findLast { it.name == "debug" }!!
            val sourceFiles = files(variant.sourceSets.map { it.javaDirectories })
            sourceDirectories.setFrom(sourceDirectories.from.plus(sourceFiles))

            fileTree("${buildDir}/tmp/kotlin-classes/debug")
        } else {
            sourceDirectories.setFrom(sourceDirectories.from.plus(kotlin!!.sourceSets.findByName("main")!!.kotlin.sourceDirectories))
            fileTree("${buildDir}/classes/kotlin/main") {
                exclude("**/*\$\$inlined*")
            }
        }

        additionalClassDirs(kotlinClasses)

        val subproject = this
        subproject.tasks.findByName("jacocoDebugConnectedTestReport")?.run {
            executionData(fileTree("${subproject.buildDir}/outputs/code_coverage/debugAndroidTest/connected/*"))
        }

        subproject.tasks.matching({ it.extensions.findByType<JacocoTaskExtension>() != null }).configureEach {
            if (!name.contains("release", true)) {
                executionData(this)
            }
        }

        // To automatically run `test` every time `./gradlew codeCoverageReport` is called,
        // you may want to set up a task dependency between them as shown below.
        // Note that this requires the `test` tasks to be resolved eagerly (see `forEach`) which
        // may have a negative effect on the configuration time of your build.
        subproject.tasks.withType<JacocoReport>().forEach {
            rootProject.tasks["jacocoTestReportMerge"].dependsOn(it)
        }
    }
}