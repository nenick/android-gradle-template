package de.nenick.gradle.plugins.basics

import org.gradle.api.Task

val Task.taskDependenciesAsStrings: List<String>
    get() = taskDependencies.getDependencies(this).map { it.toString() }