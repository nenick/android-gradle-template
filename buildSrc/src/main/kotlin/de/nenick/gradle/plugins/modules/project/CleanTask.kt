package de.nenick.gradle.plugins.modules.project

import org.gradle.api.tasks.Delete

/**
 * Task to clean the build output.
 */
open class CleanTask : Delete() {
    init {
        delete = setOf(project.buildDir)
    }
}