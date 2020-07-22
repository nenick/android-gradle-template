package de.nenick.gradle.plugins.checks

import de.nenick.gradle.tools.whenBuildSrcExist
import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import java.lang.IllegalStateException

open class KtlintOutputCheckTask : DefaultTask() {
    init {
        dependsOn(project.getTasksByName("ktlintCheck", true))
    }

    final override fun dependsOn(vararg paths: Any) = super.dependsOn(*paths)

    @TaskAction
    fun check() {
        val ktlintReportDirs = listOf(*allProjectReportDir(), *buildSrcReportDir())

        val missingKtlintReports = findMissingKtlintReports(ktlintReportDirs)
        if (missingKtlintReports.isNotEmpty()) {
            val errorMessage = "Found modules where ktlint reports are missing. " +
                    "Did you forgot to add id(\"de.nenick.ktlint-config\") or id(\"de.nenick.ktlint-android-config\")?" +
                    "\n$missingKtlintReports"
            throw GradleException(errorMessage)
        }

        val violations = findViolationReports(ktlintReportDirs)
        if (violations.isNotEmpty()) {
            throw GradleException("found modules with ktlint violations\n$violations")
        }
    }

    private fun findViolationReports(ktlintReportDirs: List<File>) = ktlintReportDirs
        .filter {
            it.listFiles()!!.any {
                when (it.extension) {
                    "txt" -> it.readText().isNotEmpty()
                    "html" -> !it.readText().contains("Congratulations, no issues found!")
                    else -> throw IllegalStateException("extension not supported yet: ${it.extension}")
                }
            }
        }
        .map { it.relativeTo(project.projectDir) }

    private fun findMissingKtlintReports(ktlintReportDirs: List<File>) = ktlintReportDirs
        .filter { it.listFiles().isNullOrEmpty() }
        .map { it.relativeTo(project.projectDir) }

    private fun allProjectReportDir() = project.allprojects
        .filter { it.plugins.hasPlugin("kotlin") || it.plugins.hasPlugin("kotlin-android") }
        .map { File(it.projectDir, "build/reports/ktlint") }
        .toTypedArray()

    private fun buildSrcReportDir() = mutableListOf<File>()
        .apply {
            whenBuildSrcExist {
                add(File(it, "build/reports/ktlint"))
            }
        }.toTypedArray()
}