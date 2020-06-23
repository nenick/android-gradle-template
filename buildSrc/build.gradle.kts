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
    implementation("com.pinterest:ktlint:0.37.2") // to get the latest improvements from ktlint itself

    // Basic tools to test the custom plugins and tasks.
    testImplementation("junit:junit:4.13")
    testImplementation("io.strikt:strikt-core:0.26.1")
}