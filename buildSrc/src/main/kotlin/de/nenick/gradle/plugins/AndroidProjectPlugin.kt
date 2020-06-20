package de.nenick.gradle.plugins

import de.nenick.gradle.plugins.tasks.CleanOutputTestTask
import de.nenick.gradle.plugins.tasks.CleanTask
import de.nenick.gradle.plugins.tasks.KtlintOutputTestTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class AndroidProjectPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.tasks.apply {
            // The default android root project does not provide a clean method. This one must be provided by self.
            register("clean", CleanTask::class) { group = "cleanup" }

            // Tasks to ensure a valid output of added gradle plugins/tasks.
            register("clean-test", CleanOutputTestTask::class) { group = "output tests" }
            register("ktlint-test", KtlintOutputTestTask::class) { group = "output tests" }
        }
    }
}