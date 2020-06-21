package de.nenick.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

class KotlinModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        addPluginKtlint(target)
    }

    private fun addPluginKtlint(target: Project) {
        target.plugins.apply(KtlintPlugin::class.java)
        target.extensions.getByType(KtlintExtension::class.java).apply {
            android.set(true)
            enableExperimentalRules.set(true)
            disabledRules.add("no-wildcard-imports")
            reporters { reporter(ReporterType.HTML) }
        }
    }
}