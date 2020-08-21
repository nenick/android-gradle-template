package de.nenick.gradle.plugins.modules.project

import de.nenick.gradle.test.tools.PluginTest
import de.nenick.gradle.test.tools.hasGroup
import de.nenick.gradle.test.tools.hasName
import de.nenick.gradle.test.tools.project.RawProject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.one

class AndroidProjectPluginTest : PluginTest<RawProject>() {

    private val pluginId = "de.nenick.android-project"

    @BeforeEach
    fun setup() {
        project = RawProject().setup { withPlugin(AndroidProjectPlugin::class) }
    }

    @Test
    override fun `applies by plugin id`() {
        project = RawProject().setup { plugins.apply(pluginId) }
        expectThat(project.plugins).one { isA<AndroidProjectPlugin>() }
    }

    @Test
    fun `adds clean task`() {
        expectThat(project.tasks).one {
            isA<CleanTask>()
            hasName("clean")
            hasGroup("cleanup")
        }
    }
}