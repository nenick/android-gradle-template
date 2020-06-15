package de.nenick.gradle.plugins

import de.nenick.gradle.plugins.tasks.CleanCheckTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.register

class ProjectTasksPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.tasks.register("clean", Delete::class) { group = "cleanup" }
        target.tasks.register("clean-check", CleanCheckTask::class) { group = "task checks (project)" }
    }
}