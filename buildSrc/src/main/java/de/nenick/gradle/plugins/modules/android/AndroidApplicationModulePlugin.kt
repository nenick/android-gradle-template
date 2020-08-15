package de.nenick.gradle.plugins.modules.android

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Applies base plugins for an android application module.
 */
open class AndroidApplicationModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply("com.android.application")
        target.plugins.apply("kotlin-android")
        target.plugins.apply("kotlin-android-extensions")
    }
}