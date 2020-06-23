package de.nenick.gradle.plugins

import org.gradle.api.Project
import org.jlleitschuh.gradle.ktlint.KtlintExtension

class AndroidModulePlugin : KotlinModulePlugin() {

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