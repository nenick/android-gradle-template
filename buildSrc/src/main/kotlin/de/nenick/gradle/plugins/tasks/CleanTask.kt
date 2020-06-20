package de.nenick.gradle.plugins.tasks

import org.gradle.api.tasks.Delete

open class CleanTask : Delete() {
    init {
        delete = setOf(project.buildDir)
    }
}