package de.nenick.gradle.plugins.base

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

/**
 * Useful configurations for each kotlin based module.
 *
 * - Ktlint for for static code analyses.
 * - Jacoco for code coverage.
 */
abstract class KotlinBasedModulePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        addPluginKtlint(target)
        addPluginJacoco(target)
    }

    private fun addPluginKtlint(target: Project) {
        target.plugins.apply(KtlintPlugin::class.java)
        target.extensions.getByType(KtlintExtension::class.java).apply {

            // Enable all checks by default and if something is annoying then disable it explicit.
            enableExperimentalRules.set(true)

            // Disable annoying rules which makes no sense for us.
            // Disable no-wildcards-imports because with modern IDEs there are rare cases when this rule could be useful.
            disabledRules.addAll("no-wildcard-imports")
            reporters { reporter(ReporterType.HTML) }
        }
    }

    private fun addPluginJacoco(target: Project) {
        target.plugins.apply(JacocoPlugin::class.java)
    }
}