package de.nenick.gradle.plugins.tasks

import de.nenick.gradle.plugins.basics.TaskTest
import de.nenick.gradle.plugins.basics.extensions.withFile
import de.nenick.gradle.plugins.basics.withAndroidModule
import de.nenick.gradle.plugins.basics.withDirectory
import de.nenick.gradle.plugins.basics.withKotlinModule
import org.gradle.api.GradleException
import org.junit.Test
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import strikt.assertions.message

class CleanOutputTestTaskTest : TaskTest<CleanOutputTestTask>(CleanOutputTestTask::class) {

    private val errorMessage = "not all contents of build folders have been deleted"

    @Test
    fun `succeed when no build directories exists`() {
        givenKotlinProject()
        whenRunTask()
        // expect that no error is thrown
    }

    @Test
    fun `succeed when no build directory is empty`() {
        givenKotlinProject() { withDirectory("build") }
        whenRunTask()
        // expect that no error is thrown
    }

    @Test
    fun `succeed when buildSrc module is used`() {
        givenEmptyProject { withDirectory("buildSrc/build") { withFile("example.content") } }
        whenRunTask()
        // expect that no error is thrown
    }

    @Test
    fun `fails when project build directory exists`() {
        givenEmptyProject { withDirectory("build") { withFile("example.content") } }

        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("$errorMessage\n[build]")
    }

    @Test
    fun `fails when module build directory exists`() {
        givenEmptyProject { withKotlinModule("module") { withDirectory("build") { withFile("example.content") } } }
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("$errorMessage\n[module/build]")
    }

    @Test
    fun `fails when multiple build directories exists`() {
        givenEmptyProject {
            withAndroidModule("module-alpha") { withDirectory("build") { withFile("example.content") } }
            withKotlinModule("module-beta") { withDirectory("build") { withFile("example.content") } }
        }
        expectThrows<GradleException> { whenRunTask() }
            .message.isEqualTo("$errorMessage\n[module-alpha/build, module-beta/build]")
    }
}