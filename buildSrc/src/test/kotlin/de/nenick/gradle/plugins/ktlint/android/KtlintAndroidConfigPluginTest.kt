package de.nenick.gradle.plugins.ktlint.android

import de.nenick.gradle.test.tools.PluginTest
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isA
import strikt.assertions.isTrue
import strikt.assertions.one

class KtlintAndroidConfigPluginTest : PluginTest() {
    private val pluginId = "de.nenick.ktlint-android-config"

    @Test
    override fun `applies by plugin id`() {
        givenEmptyProject { plugins.apply(pluginId) }
        expectThat(project.plugins).one { isA<KtlintAndroidConfigPlugin>() }
    }

    @Test
    fun `adds ktlint plugin`() {
        givenEmptyProjectWithPluginApplied()
        expectThat(project.plugins).one { isA<KtlintPlugin>() }
    }

    @Test
    fun `adjust ktlint rules`() {
        givenEmptyProjectWithPluginApplied()
        expectThat(project.extensions.getByType<KtlintExtension>()) {
            get { enableExperimentalRules.get() }.isTrue()
            get { disabledRules.get() }.contains("no-wildcard-imports")
        }
    }

    @Test
    fun `enable android specific checks`() {
        givenEmptyProjectWithPluginApplied()
        expectThat(project.extensions.getByType<KtlintExtension>()) {
            get { android.get() }.isTrue()
        }
    }

    // TODO How to test that html reporter is enabled? Don't see an easy way to access the collection yet.

    override fun givenEmptyProjectWithPluginApplied(setup: Project.() -> Unit) {
        givenEmptyProject { plugins.apply(KtlintAndroidConfigPlugin::class.java) }
        setup(project)
    }
}