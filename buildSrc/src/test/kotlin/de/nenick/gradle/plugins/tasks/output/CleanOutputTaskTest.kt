package de.nenick.gradle.plugins.tasks.output

import de.nenick.gradle.test.tools.TaskTest
import de.nenick.gradle.test.tools.extensions.withDirectory
import de.nenick.gradle.test.tools.extensions.withFile
import de.nenick.gradle.test.tools.taskDependenciesAsStrings
import de.nenick.gradle.test.tools.withAndroidModule
import de.nenick.gradle.test.tools.withKotlinModule
import org.gradle.api.GradleException
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.contains
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.message

class CleanOutputTaskTest : TaskTest<CleanOutputTask>(CleanOutputTask::class) {

    private val errorMessage = "Build folders were found in which not all of the content was cleaned."

    @Nested
    inner class Dependencies {

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
    }

    @Nested
    inner class Success {

        @Test
        fun `build directory don't exists`() {
            givenKotlinProject()
            whenRunTask()
        }

        @Test
        fun `build directory is empty`() {
            givenKotlinProject() { projectDir.withDirectory("build") }
            whenRunTask()
        }

        @Test
        fun `ignores the buildSrc build directory content`() {
            givenEmptyProject { projectDir.withDirectory("buildSrc/build") { withFile("example.content") } }
            whenRunTask()
        }
    }

    @Nested
    inner class Fail {

        @Test
        fun `project build directory has content`() {
            givenEmptyProject { projectDir.withDirectory("build") { withFile("example.content") } }
            expectThrows<GradleException> { whenRunTask() }
                .message.isEqualTo("$errorMessage\n[build]")
        }

        @Test
        fun `module build directory has content`() {
            givenEmptyProject { withKotlinModule("module") { projectDir.withDirectory("build") { withFile("example.content") } } }
            expectThrows<GradleException> { whenRunTask() }
                .message.isEqualTo("$errorMessage\n[module/build]")
        }

        @Test
        fun `multiple build directories has content`() {
            givenEmptyProject {
                withAndroidModule("module-alpha") { projectDir.withDirectory("build") { withFile("example.content") } }
                withKotlinModule("module-beta") { projectDir.withDirectory("build") { withFile("example.content") } }
            }
            expectThrows<GradleException> { whenRunTask() }
                .message.isEqualTo("$errorMessage\n[module-alpha/build, module-beta/build]")
        }
    }
}