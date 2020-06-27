package de.nenick.gradle.plugins.base

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

abstract class BaseKotlinModulePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        addPluginKtlint(target)
        addPluginJacoco(target)
    }

    private fun addPluginKtlint(target: Project) {
        target.plugins.apply(KtlintPlugin::class.java)
        target.extensions.getByType(KtlintExtension::class.java).apply {
            enableExperimentalRules.set(true)
            disabledRules.addAll("no-wildcard-imports")
            reporters { reporter(ReporterType.HTML) }
        }
    }

    private fun addPluginJacoco(target: Project) {
        target.plugins.apply(JacocoPlugin::class.java)
    }
}