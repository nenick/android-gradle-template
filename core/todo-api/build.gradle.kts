import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("de.nenick.kotlin-module")
    id("de.nenick.ktlint-config")
    id("de.nenick.jacoco-kotlin-config")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    implementation("io.ktor:ktor-client-android")
    implementation("io.ktor:ktor-client-json")
    implementation("io.ktor:ktor-client-gson")

    implementation("org.koin:koin-core")

    testImplementation("junit:junit")
    testImplementation("io.strikt:strikt-core")
    testImplementation("com.github.tomakehurst:wiremock-jre8")
}

tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }