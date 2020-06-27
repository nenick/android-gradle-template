package de.nenick.gradle.test.tools

import org.gradle.api.Task

val Task.taskDependenciesAsStrings: List<String>
    get() = taskDependencies.getDependencies(this).map { it.toString() }