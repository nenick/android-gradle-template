package de.nenick.gradle.test.tools

import de.nenick.gradle.test.tools.project.ProjectSetup
import org.junit.jupiter.api.Test

abstract class PluginTest<T : ProjectSetup<T>> {

    lateinit var project: T

    @Test
    abstract fun `applies by plugin id`()
}