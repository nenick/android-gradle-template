package de.nenick.gradle.plugins

import de.nenick.gradle.plugins.tasks.CleanCheckTask
import de.nenick.gradle.plugins.tasks.CleanTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class ProjectTasksPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.tasks.register("clean", CleanTask::class) { group = "cleanup" }
        target.tasks.register("clean-check", CleanCheckTask::class) { group = "task checks (project)" }
    }
}