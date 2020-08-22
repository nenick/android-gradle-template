package de.nenick.gradle.plugins.jacoco.android

open class JacocoAndroidExtension {

    data class ConnectedAndroidTestsSettings(
        var skipCoverageReport: Boolean = false,
        var variantForCoverage: String = "debug"
    )
    val connectedAndroidTests = ConnectedAndroidTestsSettings()
    fun connectedAndroidTests(block: ConnectedAndroidTestsSettings.() -> Unit) = block(connectedAndroidTests)

    data class AndroidUnitTestsSettings(
        var skipCoverageReport: Boolean = false,
        var variantForCoverage: String = "debug"
    )
    val androidUnitTests = AndroidUnitTestsSettings()
    fun androidUnitTests(block: AndroidUnitTestsSettings.() -> Unit) = block(androidUnitTests)
}