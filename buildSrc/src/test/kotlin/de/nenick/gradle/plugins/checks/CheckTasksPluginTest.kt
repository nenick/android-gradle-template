package de.nenick.gradle.plugins.checks

import de.nenick.gradle.test.tools.Plugin2Test
import de.nenick.gradle.test.tools.PluginTest
import de.nenick.gradle.test.tools.hasGroup
import de.nenick.gradle.test.tools.hasName
import de.nenick.gradle.test.tools.project.ProjectSetup
import de.nenick.gradle.test.tools.project.RawProject
import org.gradle.api.Project
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.one

class CheckTasksPluginTest : Plugin2Test<RawProject>() {

    private val pluginId = "de.nenick.check-tasks"

    @BeforeEach
    fun setup() {
        project = RawProject().withPlugin(CheckTasksPlugin::class)
    }

    @Test
    override fun `applies by plugin id`() {
        project = RawProject().withPlugin(pluginId)
        expectThat(project.plugins).one { isA<CheckTasksPlugin>() }
    }

    @Test
    fun `adds clean check tasks`() {
        expectThat(project.tasks).one {
            isA<CleanCheckTask>()
            hasName("clean-check")
            hasGroup("check tasks")
        }
    }

    @Test
    fun `adds ktLint check tasks`() {
        expectThat(project.tasks).one {
            isA<KtlintOutputCheckTask>()
            hasName("ktlint-check")
            hasGroup("check tasks")
        }
    }

    @Test
    fun `adds jacoco check tasks`() {
        expectThat(project.tasks).one {
            isA<JacocoOutputCheckTask>()
            hasName("jacoco-check")
            hasGroup("check tasks")
        }
    }
}