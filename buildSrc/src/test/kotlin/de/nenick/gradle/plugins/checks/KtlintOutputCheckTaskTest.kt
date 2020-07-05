package de.nenick.gradle.plugins.checks

import de.nenick.gradle.test.tools.*
import de.nenick.gradle.test.tools.extensions.withFile
import org.gradle.api.GradleException
import org.jlleitschuh.gradle.ktlint.KtlintPlugin
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.contains
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.message

class KtlintOutputCheckTaskTest : TaskTest<KtlintOutputCheckTask>(KtlintOutputCheckTask::class) {
    private val errorMessage = "found modules where ktlint reports are missing"

    @Nested
    inner class Dependencies {
        @Test
        fun `depends on all project and modules ktlintCheck tasks`() {
            givenKotlinProject {
                plugins.apply(KtlintPlugin::class.java)
                withKotlinModule("module-alpha") { plugins.apply(KtlintPlugin::class.java) }
                withKotlinModule("module-beta") { plugins.apply(KtlintPlugin::class.java) }
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
            givenEmptyProject()
            whenRunTaskActions()
        }

        @Test
        fun `reports exists`() {
            givenKotlinProject { withDirectory("build/reports/ktlint") { withFile("any-ktlint-report.txt") } }
            whenRunTaskActions()
        }
    }


    @Nested
    inner class Fail {
        @Test
        fun `no report directory was found`() {
            givenKotlinProject()
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("$errorMessage\n[build/reports/ktlint]")
        }

        @Test
        fun `no reports where found`() {
            givenKotlinProject { withDirectory("build/reports/ktlint") }
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("$errorMessage\n[build/reports/ktlint]")
        }

        @Test
        fun `buildSrc has no report`() {
            givenEmptyProject { withDirectory("buildSrc") }
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("$errorMessage\n[buildSrc/build/reports/ktlint]")
        }

        @Test
        fun `report multiple modules have no reports`() {
            givenEmptyProject {
                withAndroidModule("module-alpha")
                withKotlinModule("module-beta")
            }
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("$errorMessage\n[module-alpha/build/reports/ktlint, module-beta/build/reports/ktlint]")
        }

        @Test
        fun `report nested sub modules has no reports`() {
            givenEmptyProject { withEmptyModule("nested") { withKotlinModule("subModule") } }
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("$errorMessage\n[nested/subModule/build/reports/ktlint]")
        }
    }
}