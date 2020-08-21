package de.nenick.gradle.plugins.checks

import de.nenick.gradle.plugins.modules.project.CleanTask
import de.nenick.gradle.test.tools.TaskTest
import de.nenick.gradle.test.tools.extensions.withFile
import de.nenick.gradle.test.tools.project.RawProject
import de.nenick.gradle.test.tools.taskDependenciesAsStrings
import org.gradle.api.GradleException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.contains
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.message

class CleanCheckTaskTest : TaskTest<CleanCheckTask, RawProject>(CleanCheckTask::class) {

    private val errorMessage = "Build folders were found in which not all of the content was cleaned."

    @BeforeEach
    fun setup() {
        project = RawProject().setup { withTaskUnderTest() }
    }

    @Nested
    inner class Dependencies {
        @Test
        fun `depends on all clean tasks from project and modules`() {
            project = RawProject().setup {
                // Typical for an android project that we have to create our own root clean task on a RawProject.
                withTask("clean", CleanTask::class)
                withKotlinModule("module-alpha")
                withKotlinModule("module-beta")
                // On RawProject it important to add task after modules are created. Otherwise
                // the dependencies aren't set because they don't exist at this time. In reality
                // this is also the correct order how gradle does work.
                withTaskUnderTest()
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
            whenRunTaskActions()
        }

        @Test
        fun `build directory is empty`() {
            project.setup { withDirectory("build") }
            whenRunTaskActions()
        }

        @Test
        fun `ignores the buildSrc build directory content`() {
            project.setup { withDirectory("buildSrc/build") { withFile("example.content") } }
            whenRunTaskActions()
        }
    }

    @Nested
    inner class Fail {
        @Test
        fun `project build directory has content`() {
            project.setup { withDirectory("build") { withFile("example.content") } }
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("$errorMessage\n[build]")
        }

        @Test
        fun `module build directory has content`() {
            project.setup { withKotlinModule("module") { withDirectory("build") { withFile("example.content") } } }
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("$errorMessage\n[module/build]")
        }

        @Test
        fun `multiple build directories has content`() {
            project.setup {
                withAndroidModule("module-alpha") { withDirectory("build") { withFile("example.content") } }
                withKotlinModule("module-beta") { withDirectory("build") { withFile("example.content") } }
            }
            expectThrows<GradleException> { whenRunTaskActions() }
                .message.isEqualTo("$errorMessage\n[module-alpha/build, module-beta/build]")
        }
    }
}