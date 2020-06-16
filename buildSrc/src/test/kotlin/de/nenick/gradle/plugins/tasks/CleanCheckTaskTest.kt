package de.nenick.gradle.plugins.tasks

import de.nenick.gradle.plugins.basics.TaskTest
import de.nenick.gradle.plugins.basics.taskDependenciesAsStrings
import de.nenick.gradle.plugins.basics.withDirectory
import de.nenick.gradle.plugins.basics.withJavaModule
import org.gradle.api.GradleException
import org.junit.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.contains
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.message

class CleanCheckTaskTest : TaskTest<CleanCheckTask>(CleanCheckTask::class) {

    @Test
    fun `success when no build directories exists`() {
        givenProject()
        whenRunTask()
        // expect that no error is thrown
    }

    @Test
    fun `depends on all project and modules clean tasks`() {
        givenJavaProject {
            withJavaModule("module-alpha")
            withJavaModule("module-beta")
        }

        expectThat(task.taskDependenciesAsStrings) {
            hasSize(3)
            contains("task ':clean'")
            contains("task ':module-alpha:clean'")
            contains("task ':module-beta:clean'")
        }
    }

    @Test
    fun `runs clean first`() {
        givenJavaProject {
            withDirectory("build")
            withJavaModule("module") {
                withDirectory("build")
            }
        }
        whenRunTask()
        // expect that no error is thrown
    }

    @Test
    fun `detect project build directory`() {
        givenProject { withDirectory("build") }
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("found build folders left after clean\n[build]")
    }

    @Test
    fun `detect module build directory`() {
        givenProject { withDirectory("module/build") }
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("found build folders left after clean\n[module/build]")
    }

    @Test
    fun `detect multiple build directories`() {
        givenProject { withDirectory("build", "module/build") }
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("found build folders left after clean\n[module/build, build]")
    }

    @Test
    fun `skip build sub directory`() {
        givenProject { withDirectory("build/build") }
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("found build folders left after clean\n[build]")
    }

    @Test
    fun `skip buildSrc directory`() {
        givenProject { withDirectory("buildSrc/build") }
        whenRunTask()
        // expect that no error is thrown
    }
}


