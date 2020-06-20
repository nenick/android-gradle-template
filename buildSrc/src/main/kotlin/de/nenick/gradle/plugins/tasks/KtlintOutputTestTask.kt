package de.nenick.gradle.plugins.tasks

import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

open class KtlintOutputTestTask : DefaultTask() {

    @TaskAction
    fun check() {
        val missingKtLintReports = findMissingKtLintReports()
        if (missingKtLintReports.isNotEmpty()) {
            throw GradleException("found modules where ktLint reports are missing\n$missingKtLintReports")
        }
    }

    private fun findMissingKtLintReports() = listOf(*allProjectsReportDir(), *buildSrcReportDir())
        .filter { it.listFiles().isNullOrEmpty() }
        .map { it.relativeTo(project.projectDir) }

    private fun allProjectsReportDir() = project.allprojects
        .filter { it.plugins.hasPlugin("kotlin") || it.plugins.hasPlugin("kotlin-android") }
        .map { File(it.projectDir, "build/reports/ktlint") }
        .toTypedArray()

    private fun buildSrcReportDir() = mutableListOf<File>().apply {
        val buildSrcDir = File("${project.projectDir}/buildSrc")
        if (buildSrcDir.exists()) {
            add(File(buildSrcDir, "build/reports/ktlint"))
        }
    }.toTypedArray()
}