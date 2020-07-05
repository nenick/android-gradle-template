package de.nenick.gradle.plugins.modules.project

import de.nenick.gradle.test.tools.PluginTest
import de.nenick.gradle.test.tools.hasGroup
import de.nenick.gradle.test.tools.hasName
import org.gradle.api.Project
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.one

class AndroidProjectPluginTest : PluginTest() {
    private val pluginId = "de.nenick.android-project"

    override fun `applies by plugin id`() {
        givenEmptyProject { plugins.apply(pluginId) }
        expectThat(project.plugins).one { isA<AndroidProjectPlugin>() }
    }

    @Test
    fun `adds clean task`() {
        givenEmptyProjectWithPluginApplied()
        expectThat(project.tasks).one {
            isA<CleanTask>()
            hasName("clean")
            hasGroup("cleanup")
        }
    }

    override fun givenEmptyProjectWithPluginApplied(setup: Project.() -> Unit) {
        givenEmptyProject { plugins.apply(AndroidProjectPlugin::class.java) }
        setup(project)
    }
}