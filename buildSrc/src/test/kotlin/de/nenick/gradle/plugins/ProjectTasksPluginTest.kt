package de.nenick.gradle.plugins

import de.nenick.gradle.plugins.basics.hasGroup
import de.nenick.gradle.plugins.basics.hasName
import de.nenick.gradle.plugins.tasks.CleanCheckTask
import org.gradle.api.tasks.Delete
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Ignore
import org.junit.Test
import strikt.api.expectThat

class ProjectTasksPluginTest {

    private val project = ProjectBuilder.builder().build().also {
        it.pluginManager.apply("de.nenick.project-tasks")
    }

    @Test
    fun `adds clean task`() {
        expectThat(project.tasks.find { it is Delete }) {
            hasName("clean")
            hasGroup("cleanup")
        }
    }

    @Test
    fun `adds clean check task`() {
        expectThat(project.tasks.find { it is CleanCheckTask }) {
            hasName("clean-check")
            hasGroup("task checks (project)")
        }
    }
}


