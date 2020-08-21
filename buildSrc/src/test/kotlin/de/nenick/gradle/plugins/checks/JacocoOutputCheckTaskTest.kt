package de.nenick.gradle.plugins.checks

import com.android.build.gradle.internal.coverage.JacocoReportTask
import de.nenick.gradle.test.tools.*
import de.nenick.gradle.test.tools.extensions.withDirectory
import de.nenick.gradle.test.tools.extensions.withFile
import de.nenick.gradle.test.tools.project.ProjectSetup
import de.nenick.gradle.test.tools.project.RawProject
import org.gradle.api.GradleException
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.contains
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.message
import java.io.File

class JacocoOutputCheckTaskTest : Task2Test<JacocoOutputCheckTask, RawProject>(JacocoOutputCheckTask::class) {

    private val errorMessageNoReport = "found modules where jacoco reports are missing"

    @BeforeEach
    fun setup() {
        project = RawProject().setup { withTaskUnderTest() }
    }

    @Nested
    inner class Dependencies {
        @Test
        fun `depends on all project and modules jacocoTestReport tasks`() {
            project = RawProject().setup {
                withPlugin("de.nenick.jacoco-merge")
                withAndroidModule("module-alpha") {
                    withPlugin(JacocoPlugin::class)
                    withTask("jacocoTestReport", JacocoReportTask::class)
                }
                withKotlinModule("module-beta") { withPlugin(JacocoPlugin::class) }
                // On RawProject it important to add task after modules are created. Otherwise
                // the dependencies aren't set because they don't exist at this time. In reality
                // this is also the correct order how gradle does work.
                withTaskUnderTest()
            }
            expectThat(taskUnderTest.taskDependenciesAsStrings) {
                hasSize(3)
                contains("task ':module-alpha:jacocoTestReport'")
                contains("task ':module-beta:jacocoTestReport'")
                contains("task ':jacocoTestReportMerge'")
            }
        }
    }

    @Nested
    inner class Success {
        @Test
        fun `project has no language configured`() {
            project.setup { withValidMergedReport() }
            whenRunTaskActions()
        }
    }

    @Nested
    inner class Fail {

        @Test
        fun `report all modules without reports`() {
            project.setup {
                withValidMergedReport()
                withAndroidModule("module-alpha")
                withKotlinModule("module-beta")
            }
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo(
                    "$errorMessageNoReport\n[" +
                            "module-beta/build/reports/jacoco/test/html, " +
                            "module-alpha/build/reports/jacoco/testDebug/html, " +
                            "module-alpha/build/reports/jacoco/connectedDebug/html]"
                )
        }

        @Test
        fun `nested sub modules has no reports`() {
            project.setup {
                withValidMergedReport()
                withRawModule("nested") { withKotlinModule("subModule") }
            }
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("$errorMessageNoReport\n[nested/subModule/build/reports/jacoco/test/html]")
        }

        @Test
        fun `merged report don't exists`() {
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("$errorMessageNoReport\n[build/reports/jacoco/merged/html]")
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