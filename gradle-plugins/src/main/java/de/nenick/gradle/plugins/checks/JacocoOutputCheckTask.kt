package de.nenick.gradle.plugins.checks

import de.nenick.gradle.plugins.jacoco.android.JacocoAndroidExtension
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import java.io.File

open class JacocoOutputCheckTask : DefaultTask() {
    init {
        dependsOn(project.getTasksByName("jacocoTestReport", true))
        dependsOn(project.getTasksByName("jacocoTestReportMerge", false))
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

        val indexFiles = allExpectedIndexFiles(reportDirs)

        // Ensure that some classes are covered.
        val missingClassFiles = locateMissingClassFiles(indexFiles)
        if (missingClassFiles.isNotEmpty()) {
            throw GradleException("found modules where no class files are specified\n$missingClassFiles")
        }

        // Ensure configuration points to correct source directories.
        val missingSourceFiles = locateMissingSourceFiles(reportDirs)
        if (missingSourceFiles.isNotEmpty()) {
            throw GradleException("found modules where source files path could not be resolved\n$missingSourceFiles")
        }

        // Ensure reports are generated with the same jacoco version.
        val unexpectedVersion = locateUnexpectedVersion(indexFiles, "0.8.5")
        if (unexpectedVersion.isNotEmpty()) {
            throw GradleException("found modules where don't use jacoco version 0.8.5\n$unexpectedVersion")
        }
    }

    private fun allExpectedIndexFiles(reportDirs: List<File>) = reportDirs.map { root ->
        val reportRootContent = root.listFiles()!! // A previous test is asserting that content exists already.
        reportRootContent.find { it.name == "index.html" }
            ?: throw GradleException("unexpected missing index.html")
    }

    private fun locateMissingReports(reportDirs: List<File>) = reportDirs
        .filter { it.listFiles() == null }
        .map { it.relativeTo(project.projectDir) }

    private fun locateMissingClassFiles(indexHtmlFiles: List<File>) = indexHtmlFiles
        .filter { hasMissingClassFiles(it) }
        .map { it.relativeTo(project.projectDir) }

    private fun locateMissingSourceFiles(reportDirs: List<File>) = reportDirs
        .filter { hasAnyMissingSourceFileReports(it) }
        .map { it.relativeTo(project.projectDir) }

    private fun locateUnexpectedVersion(indexHtmlFiles: List<File>, expectedVersion: String) = indexHtmlFiles
        .filter { hasUnexpectedVersionReport(it, expectedVersion) }
        .map { it.relativeTo(project.projectDir) }

    private fun hasAnyMissingSourceFileReports(current: File): Boolean {
        if (current.isDirectory) {
            // Always we expect that any report directory contains some files.
            current.listFiles()!!.forEach {
                if (hasAnyMissingSourceFileReports(it)) return true
            }
        } else {
            return current.readText().matches(Regex(".*Source file .* was not found during generation of report.*"))
        }
        return false
    }

    private fun hasMissingClassFiles(indexHtmlFile: File): Boolean {
        return indexHtmlFile.readText().matches(Regex(".*No class files specified.*"))
    }

    private fun hasUnexpectedVersionReport(indexHtmlFile: File, expectedVersion: String): Boolean {
        return !indexHtmlFile.readText().matches(Regex(".*Created with.*$expectedVersion.*"))
    }

    private fun allExpectedReportDirs() = listOf(
        *allKotlinUnitReportDirs(),
        *allAndroidUnitReportDirs(),
        *allAndroidConnectedReportDirs(),
        *buildSrcReportDir(),
        *mergedReportDir()
    )

    private fun allKotlinUnitReportDirs() = project.allprojects
        .filter { it.plugins.hasPlugin("kotlin") }
        .map { File(it.projectDir, "build/reports/jacoco/test/html") }
        .toTypedArray()

    private fun allAndroidUnitReportDirs() = project.allprojects
        .filter { it.plugins.hasPlugin("kotlin-android") }
        .filter { !it.shouldSkipAndroidUnitTest() }
        .map { File(it.projectDir, "build/reports/jacoco/test${it.variantForAndroidUnitTest()}/html") }
        .toTypedArray()

    private fun allAndroidConnectedReportDirs() = project.allprojects
        .filter { it.plugins.hasPlugin("kotlin-android") }
        .filter { !it.shouldSkipConnectedAndroidTest() }
        .map { File(it.projectDir, "build/reports/jacoco/connected${it.variantForConnectedAndroidTest()}/html") }
        .toTypedArray()

    private fun buildSrcReportDir() = mutableListOf<File>()
        .apply {
            val buildSrcDir = File("${project.projectDir}/buildSrc")
            if (buildSrcDir.exists()) {
                add(File(buildSrcDir, "build/reports/jacoco/test/html"))
            }
        }.toTypedArray()

    private fun mergedReportDir() = arrayOf(File(project.buildDir, "reports/jacoco/merged/html"))

    private fun Project.shouldSkipConnectedAndroidTest(): Boolean {
        return extensions.getByType(JacocoAndroidExtension::class.java).connectedAndroidTests.skipCoverageReport
    }

    private fun Project.shouldSkipAndroidUnitTest(): Boolean {
        return extensions.getByType(JacocoAndroidExtension::class.java).androidUnitTests.skipCoverageReport
    }

    private fun Project.variantForConnectedAndroidTest(): String {
        return extensions.getByType(JacocoAndroidExtension::class.java).connectedAndroidTests.variantForCoverage.capitalize()
    }

    private fun Project.variantForAndroidUnitTest(): String {
        return extensions.getByType(JacocoAndroidExtension::class.java).androidUnitTests.variantForCoverage.capitalize()
    }
}