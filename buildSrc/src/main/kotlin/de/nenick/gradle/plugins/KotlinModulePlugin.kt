package de.nenick.gradle.plugins

import de.nenick.gradle.plugins.base.KotlinBasedModulePlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByName
import org.gradle.testing.jacoco.tasks.JacocoReport

/**
 * Applies useful configurations to start with a pure kotlin module.
 *
 * - Base plugins for a pure kotlin module.
 *
 * @see KotlinBasedModulePlugin for more configured stuff.
 */
open class KotlinModulePlugin : KotlinBasedModulePlugin() {

    override fun apply(target: Project) {
        super.apply(target)

        // Default plugins to write a plain kotlin module.
        target.plugins.apply("java-library")
        target.plugins.apply("kotlin")

        // Setup additional tools for a plain kotlin module.
        adjustPluginJacoco(target)
    }

    private fun adjustPluginJacoco(target: Project) {
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