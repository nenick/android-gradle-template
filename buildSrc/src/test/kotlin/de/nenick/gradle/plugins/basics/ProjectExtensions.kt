package de.nenick.gradle.plugins.basics

import java.io.File
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

fun Project.withEmptyModule(name: String, setup: Project.() -> Unit = {}) {
    val project = ProjectBuilder.builder()
        .withName(name)
        .withParent(this)
        .build()
    setup(project)
}

fun Project.withKotlinModule(name: String, setup: Project.() -> Unit = {}) {
    val project = ProjectBuilder.builder()
        .withName(name)
        .withParent(this)
        .build()
    project.plugins.apply("kotlin")
    setup(project)
}

fun Project.withAndroidModule(name: String, setup: Project.() -> Unit = {}) {
    val project = ProjectBuilder.builder()
        .withName(name)
        .withParent(this)
        .build()
    project.plugins.apply("android")
    project.plugins.apply("kotlin-android")
    setup(project)
}

fun Project.withDirectory(name: String, setup: File.() -> Unit = {}) {
    val directory = File(projectDir, name)
    directory.mkdirs()
    setup(directory)
}

fun Project.withDirectories(vararg names: String, setup: File.() -> Unit = {}) {
    names.forEach { withDirectory(it, setup) }
}