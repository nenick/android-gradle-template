plugins {
    kotlin("jvm")
    id("de.nenick.ktlint-config")
    id("de.nenick.jacoco-kotlin-config")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("com.github.tomakehurst:wiremock") { isTransitive = false }
}