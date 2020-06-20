package de.nenick.gradle.plugins.tasks

import de.nenick.gradle.plugins.basics.*
import de.nenick.gradle.plugins.basics.extensions.withFile
import org.gradle.api.GradleException
import org.junit.Test
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import strikt.assertions.message

class KtlintOutputTestTaskTest : TaskTest<KtlintOutputTestTask>(KtlintOutputTestTask::class) {

    private val errorMessage = "found modules where ktLint reports are missing"

    @Test
    fun `succeed when non kotlin project`() {
        givenEmptyProject()
        whenRunTask()
        // expect that no error is thrown
    }

    @Test
    fun `success if reports exists`() {
        givenKotlinProject {
            withDirectory("build/reports/ktlint") { withFile("any-ktlint-report.txt") }
        }
        whenRunTask()
    }

    @Test
    fun `fails when no report was found`() {
        givenKotlinProject()
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("$errorMessage\n[build/reports/ktlint]")
    }

    @Test
    fun `fails when buildSrc has no report`() {
        givenEmptyProject() { withDirectory("buildSrc") }
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