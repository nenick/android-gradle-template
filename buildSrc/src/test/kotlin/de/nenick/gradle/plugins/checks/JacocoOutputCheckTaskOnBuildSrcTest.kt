package de.nenick.gradle.plugins.checks

import de.nenick.gradle.test.tools.*
import de.nenick.gradle.test.tools.extensions.withFile
import de.nenick.gradle.test.tools.project.ProjectSetup
import de.nenick.gradle.test.tools.project.RawProject
import org.gradle.api.GradleException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import strikt.assertions.message
import java.io.File

class JacocoOutputCheckTaskOnBuildSrcTest : TaskTest<JacocoOutputCheckTask, RawProject>(JacocoOutputCheckTask::class) {
    private val errorMessageNoReport = "found modules where jacoco reports are missing"
    private val errorMessageNoSourceFiles = "found modules where source files path could not be resolved"
    private val errorMessageMultipleMatch = "found more than one matching report task"

    @BeforeEach
    fun setup() {
        project = RawProject().setup {
            withTaskUnderTest()
            withValidMergedReport()
        }
    }

    @Nested
    inner class Success {

        @Test
        fun `reports exists for buildSrc`() {
            project.setup { withDirectory("buildSrc/build/reports/jacoco/test/html") { withValidIndexHtml() } }
            whenRunTaskActions()
        }
    }

    @Nested
    inner class Fail {

        @Test
        fun `has no report`() {
            project.setup { withDirectory("buildSrc") }
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("$errorMessageNoReport\n[buildSrc/build/reports/jacoco/test/html]")
        }
    }

    private fun ProjectSetup<*>.withValidMergedReport() {
        withDirectory("build/reports/jacoco/merged/html") { withValidIndexHtml() }
    }

    private fun File.withValidIndexHtml(setup: File.() -> Unit = {}) {
        withFile("index.html") {
            writeText("Created with 0.8.5")
            setup(this)
        }
    }
}