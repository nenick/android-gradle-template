package de.nenick.gradle.plugins.modules.project

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

/**
 * Applies useful stuff for the root of an android project.
 */
class AndroidProjectPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.tasks.apply {
            // The default android root project does not provide a clean task. This one have to be provided by self.
            register("clean", CleanTask::class) { group = "cleanup" }
        }
    }
}