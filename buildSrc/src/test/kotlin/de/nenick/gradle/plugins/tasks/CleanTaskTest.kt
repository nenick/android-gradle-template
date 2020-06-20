package de.nenick.gradle.plugins.tasks

import de.nenick.gradle.plugins.basics.TaskTest
import de.nenick.gradle.plugins.basics.doesNotExists
import org.junit.Test
import strikt.api.expectThat

class CleanTaskTest : TaskTest<CleanTask>(CleanTask::class) {

    @Test
    fun `deletes build directory`() {
        givenEmptyProject { buildDir.mkdir() }
        whenRunTask()
        expectThat(project.buildDir).doesNotExists()
    }
}