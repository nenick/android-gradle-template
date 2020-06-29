package de.nenick.gradle.plugins

import de.nenick.gradle.plugins.base.KotlinBase
import de.nenick.gradle.plugins.tasks.output.CleanOutputTask
import de.nenick.gradle.plugins.tasks.CleanTask
import de.nenick.gradle.plugins.tasks.output.JacocoOutputTask
import de.nenick.gradle.plugins.tasks.output.KtlintOutputTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

/**
 * Applies useful configurations for the root of an android project.
 *
 * - The always missing clean task.
 * - Tasks to run and test the output of other common tasks.
 *
 * @see KotlinBase for more configured stuff.
 */
class AndroidProjectPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.tasks.apply {

            // The default android root project does not provide a clean method.
            // This one must be provided by self.
            register("clean", CleanTask::class) { group = "cleanup" }

            // Tasks to ensure a valid output of added gradle plugins/tasks.
            register("clean-test", CleanOutputTask::class) { group = "output tests" }
            register("ktlint-test", KtlintOutputTask::class) { group = "output tests" }
            register("jacoco-test", JacocoOutputTask::class) { group = "output tests" }
        }
    }
}