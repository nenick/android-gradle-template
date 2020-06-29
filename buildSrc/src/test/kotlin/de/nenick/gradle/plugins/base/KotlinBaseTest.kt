package de.nenick.gradle.plugins.base

import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.*

abstract class KotlinBaseTest {

    protected abstract val project: Project

    @Nested
    inner class KotlinBaseTest {

        @Test
        fun `adds ktlint plugin`() {
            expectThat(project.plugins).one { isA<KtlintPlugin>() }
            expectThat(project.extensions.getByType<KtlintExtension>()) {
                get { enableExperimentalRules.get() }.isTrue()
                get { disabledRules.get() }.contains("no-wildcard-imports")

                // TODO How to test that html reporter is enabled? Don't see an easy way to access the collection yet.
            }
        }

        @Test
        fun `adds jacoco plugin`() {
            expectThat(project.plugins).one { isA<JacocoPlugin>() }
            expectThat(project.extensions.getByType<JacocoPluginExtension>().toolVersion).isEqualTo("0.8.5")
        }
    }
}