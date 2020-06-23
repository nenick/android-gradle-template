import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-library")
    id("kotlin")
    id("nenick-kotlin-module")
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
    dependsOn(tasks.test)
    reports {
        xml.apply {
            isEnabled = true
        }
    }
    classDirectories.setFrom(
        classDirectories.files.map {
            fileTree(it) {
                exclude("**/*\$\$inlined*")
            }
        }
    )
}

tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }