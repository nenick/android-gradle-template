package de.nenick.gradle.plugins.modules.android

import de.nenick.gradle.test.tools.PluginTest
import de.nenick.gradle.test.tools.project.RawProject
import org.gradle.api.plugins.ApplicationPlugin
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsSubpluginIndicator
import org.jetbrains.kotlin.gradle.plugin.KotlinAndroidPluginWrapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.one

class AndroidApplicationModulePluginTest : PluginTest<RawProject>() {

    private val pluginId = "de.nenick.android-application-module"

    @BeforeEach
    fun setup() {
        project = RawProject().setup { withPlugin(AndroidApplicationModulePlugin::class) }
    }

    @Test
    override fun `applies by plugin id`() {
        project = RawProject().setup { plugins.apply(pluginId) }
        expectThat(project.plugins).one { isA<AndroidApplicationModulePlugin>() }
    }

    @Test
    fun `applies base plugins for kotlin android application`() {
        expectThat(project.plugins) {
            get { /* TODO why is this get call necessary to succeed? */ }
            one { isA<ApplicationPlugin>() } // android application
            one { isA<KotlinAndroidPluginWrapper>() } // android kotlin
            one { isA<AndroidExtensionsSubpluginIndicator>() } // android kotlin extensions
        }
    }
}