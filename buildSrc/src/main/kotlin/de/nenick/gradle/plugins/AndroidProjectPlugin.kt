package de.nenick.gradle.plugins

import de.nenick.gradle.plugins.base.KotlinBase
import de.nenick.gradle.plugins.tasks.CleanTask
import de.nenick.gradle.plugins.tasks.JacocoMergeTask
import de.nenick.gradle.plugins.tasks.output.CleanOutputTask
import de.nenick.gradle.plugins.tasks.output.JacocoOutputTask
import de.nenick.gradle.plugins.tasks.output.KtlintOutputTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import org.gradle.testing.jacoco.plugins.JacocoPlugin

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

            applyJacocoMergeTask(target)
        }
    }

    private fun applyJacocoMergeTask(target: Project) {
        target.plugins.apply(JacocoPlugin::class.java)
        target.tasks.register("jacocoTestReportMerge", JacocoMergeTask::class.java) {
            configureReports()
        }
    }
}