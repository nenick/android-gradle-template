plugins {
    id("java-library")
    id("kotlin")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7")
    implementation("io.ktor:ktor-client-android:1.3.2")
    implementation("io.ktor:ktor-client-json:1.3.2")
    implementation("io.ktor:ktor-client-gson:1.3.2")

    testImplementation("junit:junit")
    testImplementation("io.strikt:strikt-core")
}