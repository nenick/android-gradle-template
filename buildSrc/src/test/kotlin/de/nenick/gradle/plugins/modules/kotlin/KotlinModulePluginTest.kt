package de.nenick.gradle.plugins.modules.kotlin

import de.nenick.gradle.test.tools.PluginTest
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.one

class KotlinModulePluginTest : PluginTest() {
    private val pluginId = "de.nenick.kotlin-module"

    override fun `applies by plugin id`() {
        givenEmptyProject { plugins.apply(pluginId) }
        expectThat(project.plugins).one { isA<KotlinModulePlugin>() }
    }
    @Test
    fun `adds base kotlin module plugins`() {
        givenEmptyProjectWithPluginApplied()
        expectThat(project.plugins) {
            one { isA<JavaLibraryPlugin>() } // java library
            one { isA<KotlinPluginWrapper>() } // kotlin
        }
    }

    override fun givenEmptyProjectWithPluginApplied(setup: Project.() -> Unit) {
        givenEmptyProject { plugins.apply(KotlinModulePlugin::class.java) }
    }
}