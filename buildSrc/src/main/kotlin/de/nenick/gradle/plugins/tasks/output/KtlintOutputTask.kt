package de.nenick.gradle.plugins.tasks.output

import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

open class KtlintOutputTask : DefaultTask() {

    init {
        dependsOn(project.getTasksByName("ktlintCheck", true))
    }

    final override fun dependsOn(vararg paths: Any) = super.dependsOn(*paths)

    @TaskAction
    fun check() {
        val missingKtlintReports = findMissingKtlintReports()
        if (missingKtlintReports.isNotEmpty()) {
            throw GradleException("found modules where ktlint reports are missing\n$missingKtlintReports")
        }
        //TODO fail when reports have content
    }

    private fun findMissingKtlintReports() = listOf(*allProjectReportDir(), *buildSrcReportDir())
        .filter { it.listFiles().isNullOrEmpty() }
        .map { it.relativeTo(project.projectDir) }

    private fun allProjectReportDir() = project.allprojects
        .filter { it.plugins.hasPlugin("kotlin") || it.plugins.hasPlugin("kotlin-android") }
        .map { File(it.projectDir, "build/reports/ktlint") }
        .toTypedArray()

    private fun buildSrcReportDir() = mutableListOf<File>()
        .apply {
            val buildSrcDir = File("${project.projectDir}/buildSrc")
            if (buildSrcDir.exists()) {
                add(File(buildSrcDir, "build/reports/ktlint"))
            }
        }.toTypedArray()
}