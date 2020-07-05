package de.nenick.gradle.plugins.checks

import de.nenick.gradle.test.tools.PluginTest
import de.nenick.gradle.test.tools.hasGroup
import de.nenick.gradle.test.tools.hasName
import org.gradle.api.Project
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.one

class CheckTasksPluginTest: PluginTest() {
    private val pluginId = "de.nenick.check-tasks"

    @Test
    override fun `applies by plugin id`() {
        givenEmptyProject { plugins.apply(pluginId) }
        expectThat(project.plugins).one { isA<CheckTasksPlugin>() }
    }

    @Test
    fun `adds clean check tasks`() {
        givenEmptyProjectWithPluginApplied()
        expectThat(project.tasks).one {
            isA<CleanCheckTask>()
            hasName("clean-check")
            hasGroup("check tasks")
        }
    }

    @Test
    fun `adds ktLint check tasks`() {
        givenEmptyProjectWithPluginApplied()
        expectThat(project.tasks).one {
            isA<KtlintOutputCheckTask>()
            hasName("ktlint-check")
            hasGroup("check tasks")
        }
    }

    @Test
    fun `adds jacoco check tasks`() {
        givenEmptyProjectWithPluginApplied()
        expectThat(project.tasks).one {
            isA<JacocoOutputCheckTask>()
            hasName("jacoco-check")
            hasGroup("check tasks")
        }
    }

    override fun givenEmptyProjectWithPluginApplied(setup: Project.() -> Unit) {
        givenEmptyProject { plugins.apply(CheckTasksPlugin::class.java) }
        setup(project)
    }
}