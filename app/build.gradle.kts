plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    jacoco
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

    // running unit tests in release variant brings no value yet, so it gets the same behaviour like java lib modules
    tasks.findByName("testReleaseUnitTest")!!.enabled = false
}

jacoco {
    toolVersion = "0.8.5"
}

tasks.register("jacocoTestReport", JacocoReport::class) {
    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }

    // The lines below make sure we can report against Kotlin and exclude some Android Stuff
    val fileFilter = arrayOf(
            "**/R.class", "**/R$*.class", "**/BuildConfig.*", "**/Manifest*.*",
            "**/*Test*.*", "android/**/*.*"
    )
    val debugTree = fileTree("$project.buildDir/intermediates/javac/debug/compileDebugJavaWithJavac/classes/") { exclude(*fileFilter) }
    val kotlinClasses = fileTree("${buildDir}/tmp/kotlin-classes/debug") { exclude(*fileFilter) }
    val mainSrc = "$project.projectDir/src/main/java"

    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(debugTree, kotlinClasses)
    executionData.setFrom(fileTree(buildDir) { include("**/*.exec", "**/*coverage.ec") })
}

tasks.register("jacocoAndroidTestReport", JacocoReport::class) {
    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }

    // The lines below make sure we can report against Kotlin and exclude some Android Stuff
    val fileFilter = arrayOf(
        "**/R.class", "**/R$*.class", "**/BuildConfig.*", "**/Manifest*.*",
        "**/*Test*.*", "android/**/*.*"
    )
    val debugTree = fileTree("$project.buildDir/intermediates/javac/debug/compileDebugJavaWithJavac/classes/") { exclude(*fileFilter) }
    val kotlinClasses = fileTree("${buildDir}/tmp/kotlin-classes/debug") { exclude(*fileFilter) }
    val mainSrc = "$project.projectDir/src/main/java"

    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(debugTree, kotlinClasses)
    executionData.setFrom(fileTree(buildDir) { include("**/*coverage.ec") })
}

tasks.register("jacocoUnitTestReport", JacocoReport::class) {
    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }

    // The lines below make sure we can report against Kotlin and exclude some Android Stuff
    val fileFilter = arrayOf(
        "**/R.class", "**/R$*.class", "**/BuildConfig.*", "**/Manifest*.*",
        "**/*Test*.*", "android/**/*.*"
    )
    val debugTree = fileTree("$project.buildDir/intermediates/javac/debug/compileDebugJavaWithJavac/classes/") { exclude(*fileFilter) }
    val kotlinClasses = fileTree("${buildDir}/tmp/kotlin-classes/debug") { exclude(*fileFilter) }
    val mainSrc = "$project.projectDir/src/main/java"

    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(debugTree, kotlinClasses)
    executionData.setFrom(fileTree(buildDir) { include("**/*.exec") })
}