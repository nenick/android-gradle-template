package de.nenick.gradle.plugins.jacoco.android

import com.android.build.gradle.api.BaseVariant
import de.nenick.gradle.plugins.jacoco.android.wrapper.AndroidExtension
import de.nenick.gradle.tools.reporting
import org.gradle.api.Task
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.internal.reflect.Instantiator
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import javax.inject.Inject

/**
 * Apply and configures jacoco for a common kotlin module.
 */
open class JacocoAndroidConfigPlugin @Inject constructor(instantiator: Instantiator) : JacocoPlugin(instantiator) {
    override fun apply(target: ProjectInternal) {
        super.apply(target)

        val parentJacocoTestReportTask = target.tasks.register("jacocoTestReport").get()
        val settings = target.extensions.create("jacocoAndroid", JacocoAndroidExtension::class.java)

        createSpecificJacocoReportTask(
            target = target,
            testKind = "Connected",
            testTask = "connectedAndroidTest",
            coverageFilePattern = "*-coverage.ec",
            jacocoReportTaskClass = JacocoConnectedAndroidTestReport::class.java,
            reportPrefix = "jacoco/connected",
            parentTask = parentJacocoTestReportTask,
            userSettings = settings.connectedAndroidTests
        )

        createSpecificJacocoReportTask(
            target = target,
            testKind = "Unit",
            testTask = "test${settings.androidUnitTests.variantForCoverage.capitalize()}UnitTest",
            coverageFilePattern = "*UnitTest.exec",
            jacocoReportTaskClass = JacocoAndroidUnitTestReport::class.java,
            reportPrefix = "jacoco/test",
            parentTask = parentJacocoTestReportTask,
            userSettings = settings.androidUnitTests
        )
    }

    private fun <T : JacocoAndroidReport> createSpecificJacocoReportTask(
        target: ProjectInternal,
        testKind: String,
        jacocoReportTaskClass: Class<T>,
        reportPrefix: String,
        parentTask: Task,
        testTask: String,
        userSettings: JacocoAndroidExtension.Settings,
        coverageFilePattern: String
    ) {
        AndroidExtension.wrap(target.extensions).variants().all {
            if (name != userSettings.variantForCoverage) return@all

            val variantName = name.capitalize()
            val jacocoReportTaskName = "jacoco$variantName${testKind}TestReport"

            target.tasks.register(jacocoReportTaskName, jacocoReportTaskClass) {
                reports.html.destination = project.reporting.file("$reportPrefix$variantName/html")

                if (!target.hasProperty("coverageSkipTestTasks")) {
                    dependsOn(testTask)
                }

                configureSourceFiles(this@all, this)
                configureClassFiles(this@all, this)
                configureExecutionData(coverageFilePattern, this)
            }.also { parentTask.dependsOn(it) }
        }
    }

    private fun <T : JacocoAndroidReport> configureClassFiles(variant: BaseVariant, task: T) {
        val javaClasses = task.project.fileTree(variant.javaCompileProvider.get().destinationDir) {
            exclude("**/BuildConfig.*")
        }
        val kotlinClasses =
            task.project.fileTree("${task.project.buildDir}/tmp/kotlin-classes/${variant.name}") {
                exclude("**/*\$\$inlined*")
            }
        task.additionalClassDirs.from(javaClasses, kotlinClasses)
    }

    private fun <T : JacocoAndroidReport> configureSourceFiles(variant: BaseVariant, task: T) {
        val mainSrc = variant.sourceSets.flatMap { it.javaDirectories }
        task.additionalSourceDirs(*mainSrc.toTypedArray())
    }

    private fun <T : JacocoAndroidReport> configureExecutionData(coverageFilePattern: String, task: T) {
        task.executionData.from(task.project.fileTree(task.project.buildDir) {
            include("**/$coverageFilePattern")
        })
    }
}