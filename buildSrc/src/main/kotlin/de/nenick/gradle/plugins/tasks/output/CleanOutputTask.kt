package de.nenick.gradle.plugins.tasks.output

import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

open class CleanOutputTask : DefaultTask() {

    init {
        dependsOn(project.getTasksByName("clean", true))
    }

    final override fun dependsOn(vararg paths: Any) = super.dependsOn(*paths)

    @TaskAction
    fun checkAllBuildFoldersDeleted() {
        val buildDirectories = findNonEmptyBuildDirectories()
        if (buildDirectories.isNotEmpty()) {
            throw GradleException("Build folders were found in which not all of the content was cleaned.\n$buildDirectories")
        }
    }

    private fun findNonEmptyBuildDirectories() = project.allprojects
        .filter { it.buildDir.hasContent() }
        .map { it.buildDir.relativeTo(project.projectDir) }

    private fun File.hasContent() = listFiles()?.isNotEmpty() ?: false
}