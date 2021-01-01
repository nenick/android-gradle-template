package de.nenick.gradle.plugins.ktlint.android

import de.nenick.gradle.test.tools.PluginTest
import de.nenick.gradle.test.tools.project.AndroidProject
import org.gradle.kotlin.dsl.getByType
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isA
import strikt.assertions.isTrue
import strikt.assertions.one

class KtlintAndroidConfigPluginTest : PluginTest<AndroidProject>() {

    private val pluginId = "de.nenick.ktlint-android-config"

    @BeforeEach
    fun setup() {
        project = AndroidProject().setup { withPlugin(KtlintAndroidConfigPlugin::class) }
    }

    @Test
    override fun `applies by plugin id`() {
        project = AndroidProject().setup { plugins.apply(pluginId) }
        expectThat(project.plugins).one { isA<KtlintAndroidConfigPlugin>() }
    }

    @Test
    fun `adds ktlint plugin`() {
        expectThat(project.plugins).one { isA<KtlintPlugin>() }
    }

    @Test
    fun `adjust ktlint rules`() {
        expectThat(project.extensions.getByType<KtlintExtension>()) {
            get { enableExperimentalRules.get() }.isTrue()
            get { disabledRules.get() }.contains("no-wildcard-imports")
        }
    }

    @Test
    fun `enable android specific checks`() {
        expectThat(project.extensions.getByType<KtlintExtension>()) {
            get { android.get() }.isTrue()
        }
    }

    // TODO How to test that html reporter is enabled? Don't see an easy way to access the collection yet.
}