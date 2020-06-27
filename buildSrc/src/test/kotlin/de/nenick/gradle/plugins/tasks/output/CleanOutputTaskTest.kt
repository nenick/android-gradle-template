package de.nenick.gradle.plugins.tasks.output

import de.nenick.gradle.test.tools.TaskTest
import de.nenick.gradle.test.tools.extensions.withDirectory
import de.nenick.gradle.test.tools.extensions.withFile
import de.nenick.gradle.test.tools.taskDependenciesAsStrings
import de.nenick.gradle.test.tools.withAndroidModule
import de.nenick.gradle.test.tools.withKotlinModule
import org.gradle.api.GradleException
import org.junit.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.contains
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.message

class CleanOutputTaskTest : TaskTest<CleanOutputTask>(
    CleanOutputTask::class) {

    private val errorMessage = "not all contents of build folders have been deleted"

    @Test
    fun `depends on all project and modules clean tasks`() {
        givenKotlinProject {
            withKotlinModule("module-alpha")
            withKotlinModule("module-beta")
        }
        expectThat(taskUnderTest.taskDependenciesAsStrings) {
            hasSize(3)
            contains("task ':clean'")
            contains("task ':module-alpha:clean'")
            contains("task ':module-beta:clean'")
        }
    }

    @Test
    fun `succeed when no build directories exists`() {
        givenKotlinProject()
        whenRunTask()
        // expect that no error is thrown
    }

    @Test
    fun `succeed when no build directory is empty`() {
        givenKotlinProject() { projectDir.withDirectory("build") }
        whenRunTask()
        // expect that no error is thrown
    }

    @Test
    fun `succeed when buildSrc module is used`() {
        givenEmptyProject { projectDir.withDirectory("buildSrc/build") { withFile("example.content") } }
        whenRunTask()
        // expect that no error is thrown
    }

    @Test
    fun `fails when project build directory exists`() {
        givenEmptyProject { projectDir.withDirectory("build") { withFile("example.content") } }

        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("$errorMessage\n[build]")
    }

    @Test
    fun `fails when module build directory exists`() {
        givenEmptyProject { withKotlinModule("module") { projectDir.withDirectory("build") { withFile("example.content") } } }
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("$errorMessage\n[module/build]")
    }

    @Test
    fun `fails when multiple build directories exists`() {
        givenEmptyProject {
            withAndroidModule("module-alpha") { projectDir.withDirectory("build") { withFile("example.content") } }
            withKotlinModule("module-beta") { projectDir.withDirectory("build") { withFile("example.content") } }
        }
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("$errorMessage\n[module-alpha/build, module-beta/build]")
    }
}