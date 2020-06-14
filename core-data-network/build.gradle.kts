import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-library")
    id("kotlin")
    jacoco
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
}

ktlint {
    enableExperimentalRules.set(true)
    ignoreFailures.set(true)
    android.set(false)
    reporters {
        customReporters {
            create("html") {
                fileExtension = "html"
                dependency = "com.pinterest.ktlint:ktlint-reporter-html:0.36.0"
            }
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7")
    implementation("io.ktor:ktor-client-android:1.3.2")
    implementation("io.ktor:ktor-client-json:1.3.2")
    implementation("io.ktor:ktor-client-gson:1.3.2")

    testImplementation("junit:junit")
    testImplementation("io.strikt:strikt-core")
    testImplementation("com.github.tomakehurst:wiremock-jre8:2.26.3")
}

tasks.jacocoTestReport {
    // tests are required to run before generating the report
    dependsOn(tasks.test)
}

tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }
