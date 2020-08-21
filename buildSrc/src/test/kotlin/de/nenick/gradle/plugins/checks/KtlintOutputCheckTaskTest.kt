package de.nenick.gradle.plugins.checks

import de.nenick.gradle.test.tools.*
import de.nenick.gradle.test.tools.extensions.withFile
import de.nenick.gradle.test.tools.project.KotlinProject
import de.nenick.gradle.test.tools.project.ProjectSetup
import de.nenick.gradle.test.tools.project.RawProject
import org.gradle.api.GradleException
import org.jlleitschuh.gradle.ktlint.KtlintPlugin
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.contains
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.message
import java.lang.IllegalStateException

class KtlintOutputCheckTaskTest : TaskTest<KtlintOutputCheckTask, KotlinProject>(KtlintOutputCheckTask::class) {
    private val errorMessage = "Found modules where ktlint reports are missing. " +
            "Did you forgot to add id(\"de.nenick.ktlint-config\") or id(\"de.nenick.ktlint-android-config\")?"

    @BeforeEach
    fun setup() {
        project = KotlinProject().setup { withTaskUnderTest() }
    }

    @Nested
    inner class Dependencies {
        @Test
        fun `depends on all project and modules ktlintCheck tasks`() {
            KotlinProject().setup {
                withPlugin(KtlintPlugin::class)
                withKotlinModule("module-alpha") { withPlugin(KtlintPlugin::class) }
                withKotlinModule("module-beta") { withPlugin(KtlintPlugin::class) }
                withTaskUnderTest()
            }
            expectThat(taskUnderTest.taskDependenciesAsStrings) {
                hasSize(3)
                contains("task ':ktlintCheck'")
                contains("task ':module-alpha:ktlintCheck'")
                contains("task ':module-beta:ktlintCheck'")
            }
        }
    }

    @Nested
    inner class Success {
        @Test
        fun `non kotlin project`() {
            RawProject().setup { withTaskUnderTest() }
            whenRunTaskActions()
        }

        @Test
        fun `reports exists as html`() {
            project.setup { withReportNoErrors() }
            whenRunTaskActions()
        }

        @Test
        fun `reports exists as txt`() {
            project.setup { withDirectory("build/reports/ktlint") { withFile("any-ktlint-report.txt") } }
            whenRunTaskActions()
        }
    }

    @Nested
    inner class Fail {
        @Test
        fun `html report contains violations`() {
            project.setup {
                withDirectory("build/reports/ktlint") {
                    withFile("any-ktlint-report.html") { writeText("some violations") }
                }
            }
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("found modules with ktlint violations\n[build/reports/ktlint]")
        }

        @Test
        fun `txt report contains violations`() {
            project.setup {
                withDirectory("build/reports/ktlint") {
                    withFile("any-ktlint-report.txt") { writeText("some violations") }
                }
            }
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("found modules with ktlint violations\n[build/reports/ktlint]")
        }

        @Test
        fun `report extensions is unknown`() {
            project.setup { withDirectory("build/reports/ktlint") { withFile("any-ktlint-report.any") } }
            expectThrows<IllegalStateException> { whenRunTaskActions() }
                .message.isEqualTo("extension not supported yet: any")
        }

        @Test
        fun `no report directory was found`() {
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("$errorMessage\n[build/reports/ktlint]")
        }

        @Test
        fun `no reports where found`() {
            project.setup { withDirectory("build/reports/ktlint") }
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("$errorMessage\n[build/reports/ktlint]")
        }

        @Test
        fun `buildSrc has no report`() {
            project.setup {
                withReportNoErrors()
                withDirectory("buildSrc")
            }
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("$errorMessage\n[buildSrc/build/reports/ktlint]")
        }

        @Test
        fun `report multiple modules have no reports`() {
            project.setup {
                withAndroidModule("module-alpha")
                withKotlinModule("module-beta")
            }
            expectThrows<GradleException> { whenRunTaskActions() }.message.isEqualTo(
                "$errorMessage\n[build/reports/ktlint, module-alpha/build/reports/ktlint, module-beta/build/reports/ktlint]"
            )
        }

        @Test
        fun `report nested sub modules has no reports`() {
            project.setup {
                withReportNoErrors()
                withRawModule("nested") { withKotlinModule("subModule") }
            }
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("$errorMessage\n[nested/subModule/build/reports/ktlint]")
        }
    }

    private fun ProjectSetup<*>.withReportNoErrors() {
        withDirectory("build/reports/ktlint") {
            withFile("any-ktlint-report.html") { writeText(">Congratulations, no issues found!<") }
        }
    }
}