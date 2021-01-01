package de.nenick.gradle.plugins.ktlint.kotlin

import org.gradle.api.Project
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

/**
 * Apply and configures ktlint for a common kotlin module.
 */
open class KtlintConfigPlugin : KtlintPlugin() {
    override fun apply(target: Project) {
        super.apply(target)
        configureKtlint(target)
    }

    private fun configureKtlint(target: Project) {
        target.extensions.getByType(KtlintExtension::class.java).apply {
            // Enable all checks by default and if something is annoying then disable it explicit.
            enableExperimentalRules.set(true)

            // Disable annoying rules which makes no sense for us.
            disabledRules.addAll(
                // Disable no-wildcards-imports because with modern IDEs and build tools there are rare cases when this rule could be useful.
                "no-wildcard-imports",
                // TODO Enable again when fixed https://github.com/pinterest/ktlint/issues/527
                "import-ordering"
            )

            // Html reports are more beautiful to read.
            reporters { reporter(ReporterType.HTML) }
        }
    }
}