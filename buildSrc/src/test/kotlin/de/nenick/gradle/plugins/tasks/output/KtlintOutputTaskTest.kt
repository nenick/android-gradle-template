package de.nenick.gradle.plugins.tasks.output

import de.nenick.gradle.plugins.basics.*
import de.nenick.gradle.plugins.basics.extensions.withDirectory
import de.nenick.gradle.plugins.basics.extensions.withFile
import org.gradle.api.GradleException
import org.jlleitschuh.gradle.ktlint.KtlintPlugin
import org.junit.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.contains
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.message

class KtlintOutputTaskTest : TaskTest<KtlintOutputTask>(KtlintOutputTask::class) {

    private val errorMessage = "found modules where ktlint reports are missing"

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

    @Test
    fun `succeed when non kotlin project`() {
        givenEmptyProject()
        whenRunTask()
        // expect that no error is thrown
    }

    @Test
    fun `success if reports exists`() {
        givenKotlinProject {
            projectDir.withDirectory("build/reports/ktlint") { withFile("any-ktlint-report.txt") }
        }
        whenRunTask()
    }

    @Test
    fun `fails when no report directory was found`() {
        givenKotlinProject()
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("$errorMessage\n[build/reports/ktlint]")
    }

    @Test
    fun `fails when no reports where found`() {
        givenKotlinProject() {
            projectDir.withDirectory("build/reports/ktlint")
        }
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("$errorMessage\n[build/reports/ktlint]")
    }

    @Test
    fun `fails when buildSrc has no report`() {
        givenEmptyProject() { projectDir.withDirectory("buildSrc") }
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("$errorMessage\n[buildSrc/build/reports/ktlint]")
    }

    @Test
    fun `fails when multiple modules have no reports`() {
        givenEmptyProject {
            withAndroidModule("module-alpha")
            withKotlinModule("module-beta")
        }
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("$errorMessage\n[module-alpha/build/reports/ktlint, module-beta/build/reports/ktlint]")
    }

    @Test
    fun `fails when nested sub modules has no reports`() {
        givenEmptyProject {
            withEmptyModule("nested") { withKotlinModule("subModule") }
        }
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("$errorMessage\n[nested/subModule/build/reports/ktlint]")
    }
}