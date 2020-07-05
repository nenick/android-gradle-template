package de.nenick.gradle.plugins.jacoco.kotlin

import org.gradle.api.internal.project.ProjectInternal
import org.gradle.internal.reflect.Instantiator
import org.gradle.kotlin.dsl.getByName
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.tasks.JacocoReport
import javax.inject.Inject

/**
 * Apply and configures jacoco for a common kotlin module.
 */
open class JacocoKotlinConfigPlugin @Inject constructor(instantiator: Instantiator) : JacocoPlugin(instantiator) {
    override fun apply(target: ProjectInternal) {
        super.apply(target)
        target.tasks.getByName<JacocoReport>("jacocoTestReport").apply {
            // Depend on the unit test task to be sure the coverage data was written.
            dependsOn(target.tasks.findByName("test")!!)

            // Filter some stuff which destroys or brings no benefit to our coverage reports.
            val filteredClassDirectories = classDirectories.files.map {
                target.fileTree(it) {
                    // Exclude all generated stuff which was marked is inlined from class directory.
                    // Mainly because jacoco reports there is no source file available.
                    // This occurs when you use a third party library which provides some inline kotlin extension functions.
                    // TODO Investigate whether we hide some important info here.
                    exclude("**/*\$\$inlined*")
                }
            }

            classDirectories.setFrom(*filteredClassDirectories.toTypedArray())
        }
    }
}