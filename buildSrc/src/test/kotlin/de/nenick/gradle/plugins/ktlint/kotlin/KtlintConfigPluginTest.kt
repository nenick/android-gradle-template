package de.nenick.gradle.plugins.ktlint.kotlin

import de.nenick.gradle.test.tools.PluginTest
import de.nenick.gradle.test.tools.project.KotlinProject
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

class KtlintConfigPluginTest : PluginTest<KotlinProject>() {

    private val pluginId = "de.nenick.ktlint-config"

    @BeforeEach
    fun setup() {
        project = KotlinProject().setup { withPlugin(KtlintConfigPlugin::class) }
    }

    @Test
    override fun `applies by plugin id`() {
        project = KotlinProject().setup { withPlugin(pluginId) }
        expectThat(project.plugins).one { isA<KtlintConfigPlugin>() }
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
            get { disabledRules.get() }.contains("import-ordering")
        }
    }

    // TODO How to test that html reporter is enabled? Don't see an easy way to access the collection yet.
}