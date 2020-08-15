package de.nenick.gradle.plugins.checks

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

open class CheckTasksPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.tasks.apply {
            register("clean-check", CleanCheckTask::class) { group = "check tasks" }
            register("ktlint-check", KtlintOutputCheckTask::class) { group = "check tasks" }
            register("jacoco-check", JacocoOutputCheckTask::class) { group = "check tasks" }
        }
    }
}