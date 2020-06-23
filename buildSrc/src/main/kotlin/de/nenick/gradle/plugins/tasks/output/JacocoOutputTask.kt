package de.nenick.gradle.plugins.tasks.output

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import java.io.File

open class JacocoOutputTask : DefaultTask() {

    init {
        dependsOn(project.getTasksByName("jacocoTestReport", true))
    }

    final override fun dependsOn(vararg paths: Any) = super.dependsOn(*paths)

    @TaskAction
    fun check() {
        val reportDirs = allExpectedReportDirs()

        // Ensure that jacoco was executed for the expected target.
        val missingReports = locateMissingReports(reportDirs)
        if (missingReports.isNotEmpty()) {
            throw GradleException("found modules where jacoco reports are missing\n$missingReports")
        }

        // Ensure that some classes are covered.
        val missingClassFiles = locateMissingClassFiles(reportDirs)
        if (missingClassFiles.isNotEmpty()) {
            throw GradleException("found modules where no class files are specified\n$missingClassFiles")
        }

        // Ensure configuration points to correct source directories.
        val missingSourceFiles = locateMissingSourceFiles(reportDirs)
        if (missingSourceFiles.isNotEmpty()) {
            throw GradleException("found modules where source files path could not be resolved\n$missingSourceFiles")
        }

        // Ensure reports are generated with the same jacoco version.
        val unexpectedVersion = locateUnexpectedVersion(reportDirs, "0.8.5")
        if (unexpectedVersion.isNotEmpty()) {
            throw GradleException("found modules where don't use jacoco version 0.8.5\n$unexpectedVersion")
        }
    }

    private fun locateMissingReports(reportDirs: List<File>) = reportDirs
        .filter { it.listFiles().isNullOrEmpty() }
        .map { it.relativeTo(project.projectDir) }

    private fun locateMissingClassFiles(reportDirs: List<File>) = reportDirs
        .filter { hasMissingClassFiles(it) }
        .map { it.relativeTo(project.projectDir) }

    private fun locateMissingSourceFiles(reportDirs: List<File>) = reportDirs
        .filter { hasAnyMissingSourceFileReports(it) }
        .map { it.relativeTo(project.projectDir) }

    private fun locateUnexpectedVersion(reportDirs: List<File>, expectedVersion: String) = reportDirs
        .filter { hasUnexpectedVersionReport(it, expectedVersion) }
        .map { it.relativeTo(project.projectDir) }

    private fun hasAnyMissingSourceFileReports(current: File): Boolean {
        if(current.isDirectory) {
            // Always we expect that any report directory contains some files.
            current.listFiles()!!.forEach {
                if(hasAnyMissingSourceFileReports(it)) return true
            }
        } else {
            return current.readText().matches(Regex(".*Source file .* was not found during generation of report.*"))
        }
        return false
    }

    private fun hasMissingClassFiles(root: File): Boolean {
        val reportRootContent = root.listFiles()!! // A previous test is asserting that content exists already.

        val indexHtmlFile = reportRootContent.find { it.name == "index.html" }
            ?: throw GradleException("unexpected missing index.html")

        return indexHtmlFile.readText().matches(Regex(".*No class files specified.*"))
    }

    private fun hasUnexpectedVersionReport(root: File, expectedVersion: String): Boolean {
        val reportRootContent = root.listFiles()!! // A previous test is asserting that content exists already.

        val indexHtmlFile = reportRootContent.find { it.name == "index.html" }
            ?: throw GradleException("unexpected missing index.html")

        return !indexHtmlFile.readText().matches(Regex(".*Created with.*${expectedVersion}.*"))
    }

    private fun allExpectedReportDirs() = listOf(
        *allKotlinUnitReportDirs(),
        *allAndroidUnitReportDirs(),
        *allAndroidConnectedReportDirs(),
        *buildSrcReportDir())

    private fun allKotlinUnitReportDirs() = project.allprojects
        .filter { it.plugins.hasPlugin("kotlin") }
        .map { File(it.projectDir, "build/reports/jacoco/test/html") }
        .toTypedArray()

    private fun allAndroidUnitReportDirs() = project.allprojects
        .filter { it.plugins.hasPlugin("kotlin-android") }
        .map { File(it.projectDir, "build/reports/jacoco/testDebug/html") }
        .toTypedArray()

    private fun allAndroidConnectedReportDirs() = project.allprojects
        .filter { it.plugins.hasPlugin("kotlin-android") }
        .map { File(it.projectDir, "build/reports/jacoco/connectedDebug/html") }
        .toTypedArray()

    private fun buildSrcReportDir() = mutableListOf<File>()
        .apply {
            val buildSrcDir = File("${project.projectDir}/buildSrc")
            if (buildSrcDir.exists()) {
                add(File(buildSrcDir, "build/reports/jacoco/test/html"))
            }
        }.toTypedArray()
}