package de.nenick.gradle.plugins.jacoco.android

open class JacocoAndroidExtension {

    data class Settings(
        var skipCoverageReport: Boolean = false,
        var variantForCoverage: String = "debug"
    )
    val connectedAndroidTests = Settings()
    fun connectedAndroidTests(block: Settings.() -> Unit) = block(connectedAndroidTests)

    val androidUnitTests = Settings()
    fun androidUnitTests(block: Settings.() -> Unit) = block(androidUnitTests)
}