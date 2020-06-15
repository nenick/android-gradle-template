plugins {
    `kotlin-dsl`
    jacoco
}

repositories {
    jcenter()
}

// We have to repeat versions here because the project gradle is evaluated after the buildSrc content.
// And the current version sharing solution is bases on the project gradle file.
dependencies {
    testImplementation("junit:junit:4.13")
    testImplementation("io.strikt:strikt-core:0.26.1")
}
