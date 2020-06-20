package de.nenick.gradle.plugins

import org.gradle.kotlin.dsl.getByType
import org.gradle.testfixtures.ProjectBuilder
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.one

class KotlinModulePluginTest {

    private val project = ProjectBuilder.builder().build().also {
        it.plugins.apply("nenick-kotlin-module")
    }

    @Test
    fun `adds ktLint plugin`() {
        expectThat(project.plugins).one { isA<KtlintPlugin>() }
        expectThat(project.extensions.getByType<KtlintExtension>()) {
            assertThat("experimental checks enabled") { it.enableExperimentalRules.get() }
            assertThat("android specific checks enabled") { it.android.get() }
            assertThat("rule no-wildcards-import is disabled") { it.disabledRules.get().contains("no-wildcard-imports") }
            assertThat("rule no-wildcards-import is disabled") { it.disabledRules.get().contains("no-wildcard-imports") }
        }
    }
}