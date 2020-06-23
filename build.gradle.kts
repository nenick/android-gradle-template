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

//tasks.create("jacocoMergedTestReport", JacocoReport::class.java) {
//
//    reports {
//        xml.isEnabled = true
//        html.isEnabled = true
//    }
//
//    println("1")
//
//    val fileFilter = arrayOf( "**/R.class", "**/R$*.class", "**/BuildConfig.*", "**/Manifest*.*", "**/*Test*.*", "**/*Spec*.*", "android/**/*.*", "**/*\$inject$*")
//    println("2")
//    val debugTree = fileTree(project.rootDir){
//        include("**/build/tmp/kotlin-classes/debug/**")
//        exclude(*fileFilter)
//    }
//    println("3")
//    val mainSrc = File("$project.rootDir").walk()
//        .filter { path.matches(Regex("/.*main.java/")) }
//    println("4")
//    mainSrc.forEach { println(it) }
//    println("5")
//    sourceDirectories.setFrom(files(mainSrc))
//    println("6")
//    classDirectories.setFrom(files(debugTree))
//    println("7")
//    executionData.setFrom(fileTree(project.rootDir) {
//        include( "**/*.exec", "**/*.ec")
//    })
//    println("8")
//}