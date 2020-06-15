package de.nenick.gradle.plugins.basics

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import java.io.File

fun Project.withJavaModule(name: String, setup: Project.() -> Unit = {}) {
    val project = ProjectBuilder.builder()
        .withName(name)
        .withParent(this)
        .build()
    project.plugins.apply("java")
    setup(project)
}

fun Project.withDirectory(vararg names: String) {
    names.forEach {
        File(projectDir, it).mkdirs()
    }
}