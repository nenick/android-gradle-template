package de.nenick.gradle.plugins.jacoco.android

import org.gradle.api.tasks.Input
import org.gradle.testing.jacoco.tasks.JacocoReport

abstract class BaseJacocoAndroidReport : JacocoReport() {

    @Input
    var skipCoverageReport = false

    @Input
    var variantForCoverage = "debug"
}