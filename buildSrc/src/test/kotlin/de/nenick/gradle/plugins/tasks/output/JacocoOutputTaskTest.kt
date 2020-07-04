package de.nenick.gradle.plugins.tasks.output

import com.android.build.gradle.internal.coverage.JacocoReportTask
import de.nenick.gradle.plugins.tasks.JacocoMergeTask
import de.nenick.gradle.test.tools.*
import de.nenick.gradle.test.tools.extensions.withDirectory
import de.nenick.gradle.test.tools.extensions.withFile
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.contains
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.message
import java.io.File

class JacocoOutputTaskTest : TaskTest<JacocoOutputTask>(
    JacocoOutputTask::class
) {

    private val errorMessageNoReport = "found modules where jacoco reports are missing"
    private val errorMessageNoSourceFiles = "found modules where source files path could not be resolved"

    @Nested
    inner class Dependencies {

        @Test
        fun `depends on all project and modules jacocoTestReport tasks`() {
            givenKotlinProject {
                plugins.apply(JacocoPlugin::class.java)
                tasks.create("jacocoTestReportMerge", JacocoMergeTask::class.java)
                withAndroidModule("module-alpha") {
                    plugins.apply(JacocoPlugin::class.java)
                    tasks.create("jacocoTestReport", JacocoReportTask::class.java)
                }
                withKotlinModule("module-beta") { plugins.apply(JacocoPlugin::class.java) }
            }
            expectThat(taskUnderTest.taskDependenciesAsStrings) {
                hasSize(4)
                contains("task ':jacocoTestReport'")
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
            givenEmptyProject { withValidMergedReport() }
            whenRunTask()
        }

        @Test
        fun `reports exists for kotlin`() {
            givenKotlinProject {
                withValidMergedReport()
                projectDir.withDirectory("build/reports/jacoco/test/html") { withValidIndexHtml() }
            }
            whenRunTask()
        }

        @Test
        fun `reports exists for android`() {
            givenAndroidKotlinProject {
                withValidMergedReport()
                projectDir.withDirectory("build/reports/jacoco/testDebug/html") { withValidIndexHtml() }
                projectDir.withDirectory("build/reports/jacoco/connectedDebug/html") { withValidIndexHtml() }
            }
            whenRunTask()
        }

        @Test
        fun `reports exists for buildSrc`() {
            givenEmptyProject {
                withValidMergedReport()
                projectDir.withDirectory("buildSrc/build/reports/jacoco/testDebug/html") { withValidIndexHtml() }
            }
            expectThrows<GradleException> { whenRunTask() }
                .message.isEqualTo("$errorMessageNoReport\n[buildSrc/build/reports/jacoco/test/html]")
        }
    }

    @Nested
    inner class Fail {

        @Nested
        inner class Android {

            @Test
            fun `reports missing for unit test`() {
                givenAndroidKotlinProject {
                    withValidMergedReport()
                    projectDir.withDirectory("build/reports/jacoco/connectedDebug/html") { withValidIndexHtml() }
                }
                expectThrows<GradleException> { whenRunTask() }
                    .message.isEqualTo("$errorMessageNoReport\n[build/reports/jacoco/testDebug/html]")
            }

            @Test
            fun `reports missing for connected test`() {
                givenAndroidKotlinProject {
                    withValidMergedReport()
                    projectDir.withDirectory("build/reports/jacoco/testDebug/html") { withValidIndexHtml() }
                }
                expectThrows<GradleException> { whenRunTask() }
                    .message.isEqualTo("$errorMessageNoReport\n[build/reports/jacoco/connectedDebug/html]")
            }
        }

        @Nested
        inner class Kotlin {

            @Test
            fun `no report was found`() {
                givenKotlinProject {
                    withValidMergedReport()
                }
                expectThrows<GradleException> { whenRunTask() }
                    .message.isEqualTo("$errorMessageNoReport\n[build/reports/jacoco/test/html]")
            }
        }

        @Nested
        inner class RootProject {

            @Test
            fun `merged report don't exists`() {
                givenKotlinProject {
                    projectDir.withDirectory("build/reports/jacoco/test/html") { withValidIndexHtml() }
                }
                expectThrows<GradleException> { whenRunTask() }
                    .message.isEqualTo("$errorMessageNoReport\n[build/reports/jacoco/merged/html]")
            }
        }

        @Nested
        inner class BuildSrc {

            @Test
            fun `has no report`() {
                givenEmptyProject {
                    withValidMergedReport()
                    projectDir.withDirectory("buildSrc")
                }
                expectThrows<GradleException> { whenRunTask() }
                    .message.isEqualTo("$errorMessageNoReport\n[buildSrc/build/reports/jacoco/test/html]")
            }
        }

        @Nested
        inner class General {

            @Test
            fun `report all modules without reports`() {
                givenEmptyProject {
                    withValidMergedReport()
                    withAndroidModule("module-alpha")
                    withKotlinModule("module-beta")
                }
                expectThrows<GradleException> { whenRunTask() }
                    .message.isEqualTo(
                        "$errorMessageNoReport\n[" +
                                "module-beta/build/reports/jacoco/test/html, " +
                                "module-alpha/build/reports/jacoco/testDebug/html, " +
                                "module-alpha/build/reports/jacoco/connectedDebug/html]"
                    )
            }

            @Test
            fun `nested sub modules has no reports`() {
                givenEmptyProject {
                    withValidMergedReport()
                    withEmptyModule("nested") { withKotlinModule("subModule") }
                }
                expectThrows<GradleException> { whenRunTask() }
                    .message.isEqualTo("$errorMessageNoReport\n[nested/subModule/build/reports/jacoco/test/html]")
            }

            @Test
            fun `reports don't contain index html`() {
                givenKotlinProject {
                    withValidMergedReport()
                    projectDir.withDirectory("build/reports/jacoco/test/html") { withFile("any-jacoco-report.html") }
                }
                expectThrows<GradleException> { whenRunTask() }
                    .message.isEqualTo("unexpected missing index.html")
            }
        }

        @Nested
        inner class Sanity {

            @Test
            fun `fails when any covered source file wasn't found`() {
                givenKotlinProject {
                    withValidMergedReport()
                    projectDir.withDirectory("build/reports/jacoco/test/html") {
                        withValidIndexHtml()
                        withDirectory("my.project") {
                            withFile("any-jacoco-report.html") {
                                writeText(">Source file &quot;AnyClass.kt&quot; was not found during generation of report.<")
                            }
                        }
                    }
                }
                expectThrows<GradleException> { whenRunTask() }
                    .message.isEqualTo("$errorMessageNoSourceFiles\n[build/reports/jacoco/test/html]")
            }

            @Test
            fun `fails when used unexpected jacoco version`() {
                givenKotlinProject {
                    withValidMergedReport()
                    projectDir.withDirectory("build/reports/jacoco/test/html") {
                        withFile("any-other.html")
                        withFile("index.html") {
                            writeText(">Created with <a href=\"http://www.jacoco.org/jacoco\">JaCoCo</a> 0.7.5.201910111838<")
                        }
                    }
                }
                expectThrows<GradleException> { whenRunTask() }
                    .message.isEqualTo("found modules where don't use jacoco version 0.8.5\n[build/reports/jacoco/test/html/index.html]")
            }

            @Test
            fun `fails if no classes are specified`() {
                givenKotlinProject {
                    withValidMergedReport()
                    projectDir.withDirectory("build/reports/jacoco/test/html") {
                        withValidIndexHtml {
                            appendText(">No class files specified.<")
                        }
                    }
                }
                expectThrows<GradleException> { whenRunTask() }
                    .message.isEqualTo("found modules where no class files are specified\n[build/reports/jacoco/test/html/index.html]")
            }
        }
    }

    private fun Project.withValidMergedReport() {
        projectDir.withDirectory("build/reports/jacoco/merged/html") { withValidIndexHtml() }
    }

    private fun File.withValidIndexHtml(setup: File.() -> Unit = {}) {
        withFile("index.html") {
            writeText("Created with 0.8.5")
            setup(this)
        }
    }

}