plugins {
    `kotlin-dsl`
    id("jacoco")
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
}

repositories {
    jcenter()
    google()
    maven("https://plugins.gradle.org/m2/")
}

dependencies {

    // Default android/kotlin related build plugins.
    implementation("com.android.tools.build:gradle:4.0.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")

    // Plugins we want to provide with common pre configurations for this project.
    implementation("org.jlleitschuh.gradle:ktlint-gradle:9.2.1")
    implementation(gradleApi())
    implementation(localGroovy())
    // Basic tools to test the custom plugins and tasks.
    testImplementation("junit:junit:4.13")
    testImplementation("io.strikt:strikt-core:0.26.1")
}

ktlint {
    disabledRules.set(listOf("no-wildcard-imports"))
}