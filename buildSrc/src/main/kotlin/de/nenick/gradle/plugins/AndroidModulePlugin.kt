package de.nenick.gradle.plugins

import de.nenick.gradle.plugins.base.KotlinBasedModulePlugin
import org.gradle.api.Project
import org.jlleitschuh.gradle.ktlint.KtlintExtension

/**
 * Applies useful configurations to start with an android (app/lib??) module.
 *
 * - Base plugins you need for a pure kotlin module.
 * - Jacoco for code coverage.
 *
 * @see KotlinBasedModulePlugin for more configured stuff.
 */
class AndroidModulePlugin : KotlinBasedModulePlugin() {

    override fun apply(target: Project) {
        super.apply(target)
        adjustPluginKtlint(target)
    }

    private fun adjustPluginKtlint(target: Project) {
        target.extensions.getByType(KtlintExtension::class.java).apply {
            android.set(true)
        }
    }
}