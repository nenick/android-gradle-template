plugins {
    `kotlin-dsl`
    id("jacoco")
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
}

ktlint {
    ignoreFailures.set(true) // It's annoying when you develop and the gradle import does not work any more because of style issues.
    disabledRules.set(
        setOf(
            "import-ordering", // TODO Enable again when fixed https://github.com/pinterest/ktlint/issues/527
            "no-wildcard-imports" // With modern IDEs this seems not necessary.
        )
    )
}

tasks.test {
    // Starting with version 4.6, Gradle provides native support for executing tests on the JUnit Platform.
    useJUnitPlatform()

    // Print failed test to console to show hints for the reason when gradle import failed.
    testLogging {
        events("failed")
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    val filteredClassDirectories = classDirectories.files.map {
        fileTree(it) {
            exclude("**/*\$\$inlined*")
        }
    }
    classDirectories.setFrom(*filteredClassDirectories.toTypedArray())
}

tasks.check {
    dependsOn(tasks.jacocoTestReport)
}

repositories {
    jcenter()
    google()
    maven("https://plugins.gradle.org/m2/")
}

dependencies {
    // Default android/kotlin related build plugins.
    implementation("com.android.tools.build:gradle:4.1.0-rc02")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")

    // Plugins we want to provide with common pre configurations for this project.
    implementation("org.jlleitschuh.gradle:ktlint-gradle:9.2.1")
    // Note: Jacoco dependency is already available through the gradle android build dependency.

    // Basic tools to test the custom plugins and tasks.
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
    testImplementation("io.strikt:strikt-core:0.26.1")
}