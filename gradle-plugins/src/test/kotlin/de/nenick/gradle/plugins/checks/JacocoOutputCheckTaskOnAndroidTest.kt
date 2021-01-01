package de.nenick.gradle.plugins.checks

import de.nenick.gradle.plugins.jacoco.android.JacocoAndroidConfigPlugin
import de.nenick.gradle.plugins.jacoco.android.JacocoAndroidExtension
import de.nenick.gradle.test.tools.TaskTest
import de.nenick.gradle.test.tools.extensions.withFile
import de.nenick.gradle.test.tools.project.AndroidProject
import de.nenick.gradle.test.tools.project.ProjectSetup
import org.gradle.api.GradleException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import strikt.assertions.message
import java.io.File

class JacocoOutputCheckTaskOnAndroidTest : TaskTest<JacocoOutputCheckTask, AndroidProject>(JacocoOutputCheckTask::class) {

    private val errorMessageNoReport = "found modules where jacoco reports are missing"

    @BeforeEach
    fun setup() {
        project = AndroidProject().setup {
            withPlugin(JacocoAndroidConfigPlugin::class)
            withTaskUnderTest()
            withValidMergedReport()
        }
    }

    @Nested
    inner class Success {
        @Test
        fun `reports exists for android`() {
            project.setup {
                withDirectory("build/reports/jacoco/testDebug/html") { withValidIndexHtml() }
                withDirectory("build/reports/jacoco/connectedDebug/html") { withValidIndexHtml() }
            }
            whenRunTaskActions()
        }

        @Test
        fun `skip missing android unit tests reports`() {
            project.setup {
                withDirectory("build/reports/jacoco/connectedDebug/html") { withValidIndexHtml() }
                extensions.getByType(JacocoAndroidExtension::class.java).androidUnitTests.skipCoverageReport = true
            }
            whenRunTaskActions()
        }

        @Test
        fun `skip missing connected android tests reports`() {
            project.setup {
                withDirectory("build/reports/jacoco/testDebug/html") { withValidIndexHtml() }
                extensions.getByType(JacocoAndroidExtension::class.java).connectedAndroidTests.skipCoverageReport = true
            }
            whenRunTaskActions()
        }

        @Test
        fun `different variant for android unit test reports`() {
            project.setup {
                withDirectory("build/reports/jacoco/testAnotherOne/html") { withValidIndexHtml() }
                withDirectory("build/reports/jacoco/connectedDebug/html") { withValidIndexHtml() }
                extensions.getByType(JacocoAndroidExtension::class.java).androidUnitTests.variantForCoverage = "anotherOne"
            }
            whenRunTaskActions()
        }

        @Test
        fun `different variant for connected android test reports`() {
            project.setup {
                withDirectory("build/reports/jacoco/testDebug/html") { withValidIndexHtml() }
                withDirectory("build/reports/jacoco/connectedAnotherOne/html") { withValidIndexHtml() }
                extensions.getByType(JacocoAndroidExtension::class.java).connectedAndroidTests.variantForCoverage = "anotherOne"
            }
            whenRunTaskActions()
        }
    }

    @Nested
    inner class Fail {
        @Test
        fun `reports missing for android unit test`() {
            project.setup {
                withDirectory("build/reports/jacoco/connectedDebug/html") { withValidIndexHtml() }
            }
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("$errorMessageNoReport\n[build/reports/jacoco/testDebug/html]")
        }

        @Test
        fun `reports missing for connected android test`() {
            project.setup {
                withDirectory("build/reports/jacoco/testDebug/html") { withValidIndexHtml() }
            }
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("$errorMessageNoReport\n[build/reports/jacoco/connectedDebug/html]")
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