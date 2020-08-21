package de.nenick.gradle.plugins.modules.kotlin

import de.nenick.gradle.test.tools.PluginTest
import de.nenick.gradle.test.tools.project.RawProject
import org.gradle.api.plugins.JavaLibraryPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.one

class KotlinModulePluginTest : PluginTest<RawProject>() {

    private val pluginId = "de.nenick.kotlin-module"

    @BeforeEach
    fun setup() {
        project = RawProject().setup { withPlugin(KotlinModulePlugin::class) }
    }

    override fun `applies by plugin id`() {
        project = RawProject().setup { plugins.apply(pluginId) }
        expectThat(project.plugins).one { isA<KotlinModulePlugin>() }
    }
    @Test
    fun `adds base kotlin module plugins`() {
        expectThat(project.plugins) {
            one { isA<JavaLibraryPlugin>() } // java library
            one { isA<KotlinPluginWrapper>() } // kotlin
        }
    }
}