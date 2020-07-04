package de.nenick.gradle.plugins.tasks

import de.nenick.gradle.plugins.base.whenBuildSrcExist
import org.gradle.api.reporting.ReportingExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.testing.jacoco.tasks.JacocoReport
import java.io.File

open class JacocoMergeTask : JacocoReport() {

    init {
        // Ensure all coverage data in sub modules is produced.
        dependsOn(project.getTasksByName("jacocoTestReport", true))
        includeBuildSrc()
    }

    final override fun dependsOn(vararg paths: Any) = super.dependsOn(*paths)

    /**
     * Have to be called after task registration.
     *
     * <pre>
     *     target.tasks.register("jacocoTestReportMerge", JacocoMergeTask::class.java) {
     *        configureReports()
     *     }
     * </pre>
     *
     * The report destination will be overwritten when this task becomes registered.
     */
    fun configureReports() {
        // Report as html in the same directory style like sub modules do.
        reports.html.apply {
            isEnabled = true
            destination = project.extensions.getByType<ReportingExtension>().file("jacoco/merged/html")
        }

        // Report as xml to support cloud coverage reporting tools.
        reports.xml.apply {
            isEnabled = true
            destination = project.extensions.getByType<ReportingExtension>().file("jacoco/merged/jacocoTestReport.xml")
        }
    }

    private fun includeBuildSrc() {
        whenBuildSrcExist {
            additionalSourceDirs(File(it, "src/main/kotlin"))
            additionalClassDirs(project.fileTree(File(it, "/build/classes/kotlin/main")){
                exclude("**/*\$\$inlined*")
            })
            executionData(File(it, "build/jacoco/test.exec"))
        }
    }
}