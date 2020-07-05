package de.nenick.gradle.plugins.ktlint.android

import de.nenick.gradle.plugins.ktlint.kotlin.KtlintConfigPlugin
import org.gradle.api.Project
import org.jlleitschuh.gradle.ktlint.KtlintExtension

/**
 * Apply and configures ktlint for a common android module based on kotlin.
 */
open class KtlintAndroidConfigPlugin : KtlintConfigPlugin() {
    override fun apply(target: Project) {
        super.apply(target)
        configureKtlint(target)
    }

    private fun configureKtlint(target: Project) {
        target.extensions.getByType(KtlintExtension::class.java).apply {
            // Enable android specific configurations.
            android.set(true)
        }
    }
}