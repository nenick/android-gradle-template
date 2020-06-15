package de.nenick.gradle.plugins.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

open class CleanCheckTask : DefaultTask() {

    init {
        dependsOn(project.getTasksByName("clean", true))
    }

    final override fun dependsOn(vararg paths: Any) = super.dependsOn(*paths)

    @TaskAction
    fun checkAllBuildFoldersRemoved() {
        val buildDirectories = project.projectDir.walk()
            .onEnter { !it.parent.endsWith("build") && !it.endsWith("buildSrc") }
            .filter { it.endsWith("build") }
            .toList()

        if (buildDirectories.count() > 0) {
            val relativePaths = buildDirectories.map { it.relativeTo(project.projectDir) }
            throw GradleException("found build folders left after clean\n$relativePaths")
        }
    }
}