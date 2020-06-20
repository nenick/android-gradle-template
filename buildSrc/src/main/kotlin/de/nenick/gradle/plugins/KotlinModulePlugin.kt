package de.nenick.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin

class KotlinModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply(KtlintPlugin::class.java)
        target.extensions.getByType(KtlintExtension::class.java).apply {
            android.set(true)
            enableExperimentalRules.set(true)
            disabledRules.add("no-wildcard-imports")
            reporters {
                customReporters.apply {
                    create("html") {
                        fileExtension = "html"
                        dependency = "com.pinterest.ktlint:ktlint-reporter-html:0.36.0"
                    }
                }
            }
        }
    }
}