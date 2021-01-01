package de.nenick.gradle.plugins.modules.kotlin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Applies base plugins for a pure kotlin module.
 */
open class KotlinModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        // Default plugins to write a plain kotlin module.
        target.plugins.apply("java-library") // TODO Check if this plugin really necessary? (Added by AndroidStudio by default)
        target.plugins.apply("kotlin")
    }
}