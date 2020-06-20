package de.nenick.gradle.plugins.tasks

import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

open class CleanOutputTestTask : DefaultTask() {

    @TaskAction
    fun checkAllBuildFoldersDeleted() {
        val buildDirectories = findNonEmptyBuildDirectories()
        if (buildDirectories.isNotEmpty()) {
            throw GradleException("not all contents of build folders have been deleted\n$buildDirectories")
        }
    }

    private fun findNonEmptyBuildDirectories() = project.allprojects
        .filter { it.buildDir.hasContent() }
        .map { it.buildDir.relativeTo(project.projectDir) }

    private fun File.hasContent() = listFiles()?.isNotEmpty() ?: false
}