package de.nenick.gradle.plugins.modules.project

import de.nenick.gradle.test.tools.TaskTest
import de.nenick.gradle.test.tools.doesNotExists
import de.nenick.gradle.test.tools.exists
import de.nenick.gradle.test.tools.project.RawProject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat

class CleanTaskTest : TaskTest<CleanTask, RawProject>(CleanTask::class) {

    @BeforeEach
    fun setup() {
        project = RawProject().setup { withTaskUnderTest() }
    }

    @Test
    fun `deletes build directory`() {
        project.setup { withDirectory(project.buildDir) }
        expectThat(project.buildDir).exists()
        whenRunTaskActions()
        expectThat(project.buildDir).doesNotExists()
    }
}