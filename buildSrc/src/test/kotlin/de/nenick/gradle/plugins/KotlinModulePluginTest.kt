package de.nenick.gradle.plugins

import org.gradle.kotlin.dsl.getByType
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
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
    fun `adds ktlint plugin`() {
        expectThat(project.plugins).one { isA<KtlintPlugin>() }
        expectThat(project.extensions.getByType<KtlintExtension>()) {
            assertThat("experimental checks enabled") { it.enableExperimentalRules.get() }
            assertThat("rule no-wildcard-imports is disabled") { it.disabledRules.get().contains("no-wildcard-imports") }
            // TODO How to test that?
            // assertThat("html reporter is enabled") {  }
        }
    }

    @Test
    fun `adds jacoco plugin`() {
        expectThat(project.plugins).one { isA<JacocoPlugin>() }
        expectThat(project.extensions.getByType<JacocoPluginExtension>()) {
            assertThat("use specific version") { it.toolVersion == "0.8.5" }
        }
    }
}