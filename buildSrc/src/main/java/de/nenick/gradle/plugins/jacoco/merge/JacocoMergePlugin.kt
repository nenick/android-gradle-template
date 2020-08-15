package de.nenick.gradle.plugins.jacoco.merge

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.reporting.ReportingExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.testing.jacoco.plugins.JacocoPlugin

/**
 * Support to merge the jacoco reports for a multi module project.
 */
open class JacocoMergePlugin : Plugin<Project> {
    private val commonJacocoReportTask = "jacocoTestReport"
    private val mergeTaskName = "jacocoTestReportMerge"
    private val htmlReportDir = "jacoco/merged/html"
    private val xmlReportFile = "jacoco/merged/jacocoTestReport.xml"

    override fun apply(target: Project) {
        target.plugins.apply(JacocoPlugin::class.java)

        val jacocoMergeTask = target.tasks.register(mergeTaskName, JacocoMergeTask::class.java) {
            // Html report in the same style how sub modules do.
            reports.html.apply {
                isEnabled = true
                destination = project.extensions.getByType<ReportingExtension>().file(htmlReportDir)
            }

            // Xml report to support reporting tools coverage in the cloud.
            reports.xml.apply {
                isEnabled = true
                destination = project.extensions.getByType<ReportingExtension>().file(xmlReportFile)
            }
        }

        // Ensure all coverage data in sub modules is produced. Typically the jacoco report tasks depend on the test tasks.
        jacocoMergeTask.get().dependsOn(target.getTasksByName(commonJacocoReportTask, true))
    }
}