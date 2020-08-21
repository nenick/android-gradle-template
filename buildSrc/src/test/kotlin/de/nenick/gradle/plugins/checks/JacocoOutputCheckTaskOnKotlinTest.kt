package de.nenick.gradle.plugins.checks

import de.nenick.gradle.test.tools.TaskTest
import de.nenick.gradle.test.tools.extensions.withDirectory
import de.nenick.gradle.test.tools.extensions.withFile
import de.nenick.gradle.test.tools.project.KotlinProject
import de.nenick.gradle.test.tools.project.ProjectSetup
import org.gradle.api.GradleException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import strikt.assertions.message
import java.io.File

class JacocoOutputCheckTaskOnKotlinTest : TaskTest<JacocoOutputCheckTask, KotlinProject>(JacocoOutputCheckTask::class) {
    private val errorMessageNoReport = "found modules where jacoco reports are missing"
    private val errorMessageNoSourceFiles = "found modules where source files path could not be resolved"

    @BeforeEach
    fun setup() {
        project = KotlinProject().setup {
            withTaskUnderTest()
            withValidMergedReport()
        }
    }

    @Nested
    inner class Success {
        @Test
        fun `reports exists for kotlin`() {
            project.setup { withDirectory("build/reports/jacoco/test/html") { withValidIndexHtml() } }
            whenRunTaskActions()
        }
    }

    @Nested
    inner class Fail {

        @Test
        fun `no report was found`() {
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("$errorMessageNoReport\n[build/reports/jacoco/test/html]")
        }

        @Test
        fun `reports don't contain index html`() {
            project.setup { withDirectory("build/reports/jacoco/test/html") { withFile("any-jacoco-report.html") } }
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("unexpected missing index.html")
        }

        @Test
        fun `fails when any covered source file wasn't found`() {
            project.setup {
                withDirectory("build/reports/jacoco/test/html") {
                    withValidIndexHtml()
                    withDirectory("my.project") {
                        withFile("any-jacoco-report.html") {
                            writeText(">Source file &quot;AnyClass.kt&quot; was not found during generation of report.<")
                        }
                    }
                }
            }
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("$errorMessageNoSourceFiles\n[build/reports/jacoco/test/html]")
        }

        @Test
        fun `fails when used unexpected jacoco version`() {
            project.setup {
                withDirectory("build/reports/jacoco/test/html") {
                    withFile("any-other.html")
                    withFile("index.html") {
                        writeText(">Created with <a href=\"http://www.jacoco.org/jacoco\">JaCoCo</a> 0.7.5.201910111838<")
                    }
                }
            }
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("found modules where don't use jacoco version 0.8.5\n[build/reports/jacoco/test/html/index.html]")
        }

        @Test
        fun `fails if no classes are specified`() {
            project.setup {
                withDirectory("build/reports/jacoco/test/html") {
                    withValidIndexHtml {
                        appendText(">No class files specified.<")
                    }
                }
            }
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("found modules where no class files are specified\n[build/reports/jacoco/test/html/index.html]")
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