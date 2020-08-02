package de.nenick.gradle.plugins.jacoco.android

import org.gradle.api.tasks.Input
import org.gradle.testing.jacoco.tasks.JacocoReport

open class JacocoAndroidReport : JacocoReport() {

    @Input
    var skipUnitTest = false

    @Input
    var skipAndroidTest = false
}