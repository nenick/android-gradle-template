package de.nenick.gradle.plugins.modules.android

import de.nenick.gradle.test.tools.PluginTest
import org.gradle.api.Project
import org.gradle.api.plugins.ApplicationPlugin
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsSubpluginIndicator
import org.jetbrains.kotlin.gradle.plugin.KotlinAndroidPluginWrapper
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.one

class AndroidApplicationModulePluginTest : PluginTest() {
    private val pluginId = "de.nenick.android-application-module"

    @Test
    override fun `applies by plugin id`() {
        givenEmptyProject { plugins.apply(pluginId) }
        expectThat(project.plugins).one { isA<AndroidApplicationModulePlugin>() }
    }

    @Test
    fun `applies base plugins for kotlin android application`() {
        givenEmptyProjectWithPluginApplied()
        expectThat(project.plugins) {
            get { forEach { println(it) } }
            one { isA<ApplicationPlugin>() } // android application
            one { isA<KotlinAndroidPluginWrapper>() } // android kotlin
            one { isA<AndroidExtensionsSubpluginIndicator>() } // android kotlin extensions
        }
    }

    override fun givenEmptyProjectWithPluginApplied(setup: Project.() -> Unit) {
        givenEmptyProject { plugins.apply(AndroidApplicationModulePlugin::class.java) }
    }
}