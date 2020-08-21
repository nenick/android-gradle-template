package de.nenick.gradle.test.tools

import org.gradle.api.Project
import org.junit.jupiter.api.Test

abstract class PluginTest : ProjectExtensions {

    override lateinit var project: Project

    @Test
    abstract fun `applies by plugin id`()

    abstract fun givenEmptyProjectWithPluginApplied(setup: Project.() -> Unit = {})
}