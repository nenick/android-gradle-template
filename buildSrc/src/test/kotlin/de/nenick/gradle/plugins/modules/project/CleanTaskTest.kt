package de.nenick.gradle.plugins.modules.project

import de.nenick.gradle.test.tools.TaskTest
import de.nenick.gradle.test.tools.doesNotExists
import de.nenick.gradle.test.tools.exists
import de.nenick.gradle.test.tools.extensions.withDirectory
import org.junit.jupiter.api.Test
import strikt.api.expectThat

class CleanTaskTest : TaskTest<CleanTask>(CleanTask::class) {
    @Test
    fun `deletes build directory`() {
        givenEmptyProject { withDirectory(project.buildDir) }
        expectThat(project.buildDir).exists()
        whenRunTaskActions()
        expectThat(project.buildDir).doesNotExists()
    }
}