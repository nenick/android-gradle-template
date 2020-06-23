package de.nenick.gradle.plugins.tasks.output

import com.android.build.gradle.internal.coverage.JacocoReportTask
import de.nenick.gradle.plugins.basics.*
import de.nenick.gradle.plugins.basics.extensions.withDirectory
import de.nenick.gradle.plugins.basics.extensions.withFile
import org.gradle.api.GradleException
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.junit.Test
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

    @Test
    fun `depends on all project and modules jacocoTestReport tasks`() {
        givenKotlinProject {
            plugins.apply(JacocoPlugin::class.java)
            withAndroidModule("module-alpha") {
                plugins.apply(JacocoPlugin::class.java)
                tasks.create("jacocoTestReport", JacocoReportTask::class.java)
            }
            withKotlinModule("module-beta") { plugins.apply(JacocoPlugin::class.java) }
        }
        expectThat(taskUnderTest.taskDependenciesAsStrings) {
            hasSize(3)
            contains("task ':jacocoTestReport'")
            contains("task ':module-alpha:jacocoTestReport'")
            contains("task ':module-beta:jacocoTestReport'")
        }
    }

    @Test
    fun `succeed when non kotlin project`() {
        givenEmptyProject()
        whenRunTask()
        // expect that no error is thrown
    }

    @Test
    fun `success if reports exists for kotlin`() {
        givenKotlinProject {
            projectDir.withDirectory("build/reports/jacoco/test/html") { withValidIndexHtml() }
        }
        whenRunTask()
    }

    @Test
    fun `success if reports exists for android unit and connected test`() {
        givenAndroidKotlinProject {
            projectDir.withDirectory("build/reports/jacoco/testDebug/html") { withValidIndexHtml() }
            projectDir.withDirectory("build/reports/jacoco/connectedDebug/html") { withValidIndexHtml() }
        }
        whenRunTask()
    }

    @Test
    fun `fail when reports missing for android unit test`() {
        givenAndroidKotlinProject {
            projectDir.withDirectory("build/reports/jacoco/connectedDebug/html") { withValidIndexHtml() }
        }
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("$errorMessageNoReport\n[build/reports/jacoco/testDebug/html]")
    }

    @Test
    fun `fail when reports missing for android connected test`() {
        givenAndroidKotlinProject {
            projectDir.withDirectory("build/reports/jacoco/testDebug/html") { withValidIndexHtml() }
        }
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("$errorMessageNoReport\n[build/reports/jacoco/connectedDebug/html]")
    }

    @Test
    fun `fails when no report was found`() {
        givenKotlinProject()
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("$errorMessageNoReport\n[build/reports/jacoco/test/html]")
    }

    @Test
    fun `fails when buildSrc has no report`() {
        givenEmptyProject() { projectDir.withDirectory("buildSrc") }
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("$errorMessageNoReport\n[buildSrc/build/reports/jacoco/test/html]")
    }

    @Test
    fun `fails when multiple modules have no reports`() {
        givenEmptyProject {
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
    fun `fails when nested sub modules has no reports`() {
        givenEmptyProject {
            withEmptyModule("nested") { withKotlinModule("subModule") }
        }
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("$errorMessageNoReport\n[nested/subModule/build/reports/jacoco/test/html]")
    }

    @Test
    fun `fails when any covered source file wasn't found`() {
        givenKotlinProject {
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
            projectDir.withDirectory("build/reports/jacoco/test/html") {
                withFile("index.html") {
                    writeText(">Created with <a href=\"http://www.jacoco.org/jacoco\">JaCoCo</a> 0.7.5.201910111838<")
                }
            }
        }
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("found modules where don't use jacoco version 0.8.5\n[build/reports/jacoco/test/html]")
    }

    @Test
    fun `fails if no classes are specified`() {
        givenKotlinProject {
            projectDir.withDirectory("build/reports/jacoco/test/html") {
                withValidIndexHtml {
                    appendText(">No class files specified.<")
                }
            }
        }
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("found modules where no class files are specified\n[build/reports/jacoco/test/html]")
    }

    @Test
    fun `fail if reports don't contain index html`() {
        givenKotlinProject {
            projectDir.withDirectory("build/reports/jacoco/test/html") { withFile("any-jacoco-report.html") }
        }
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("unexpected missing index.html")
    }

    private fun File.withValidIndexHtml(setup: File.() -> Unit = {}) {
        withFile("index.html") {
            writeText("Created with 0.8.5")
            setup(this)
        }
    }
}