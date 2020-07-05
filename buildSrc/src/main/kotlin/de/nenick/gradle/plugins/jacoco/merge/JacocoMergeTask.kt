package de.nenick.gradle.plugins.jacoco.merge

import de.nenick.gradle.tools.whenBuildSrcExist
import org.gradle.testing.jacoco.tasks.JacocoReport
import java.io.File

/**
 * Task to merge the jacoco reports for a multi module project.
 */
open class JacocoMergeTask : JacocoReport() {
    private val buildSrcSourceDir = "src/main/kotlin"
    private val buildSrcBuildDir = "/build/classes/kotlin/main"
    private val buildSrcCoverageDataFile = "build/jacoco/test.exec"
    private val excludeMissingSources = listOf(
        "**/*\$\$inlined*"
    )

    init {
        includeBuildSrc()
    }

    private fun includeBuildSrc() {
        whenBuildSrcExist {
            additionalSourceDirs(File(it, buildSrcSourceDir))
            additionalClassDirs(project.fileTree(File(it, buildSrcBuildDir)) {
                exclude(excludeMissingSources)
            })
            executionData(File(it, buildSrcCoverageDataFile))
        }
    }
}