package de.nenick.gradle.test.tools

import com.android.build.gradle.AppExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.testfixtures.ProjectBuilder
import java.io.File

interface ProjectExtensions {
    var project: Project

    fun givenEmptyProject(setup: Project.() -> Unit = {}): Project {
        project = ProjectBuilder.builder().build()
        setup(project)
        return project
    }

    fun givenKotlinProject(setup: Project.() -> Unit = {}): Project {
        return givenEmptyProject {
            plugins.apply("kotlin")
            setup(this)
        }
    }

    fun givenJavaProject(setup: Project.() -> Unit = {}) {
        givenEmptyProject {
            plugins.apply("java")
            setup(this)
        }
    }

    fun givenAndroidKotlinProject(setup: Project.() -> Unit = {}) {
        givenEmptyProject {
            applyAndroidApplication()
            setup(this)
        }
    }

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
        project.applyAndroidApplication()
        setup(project)
    }

    fun Project.withDirectory(directory: File, setup: File.() -> Unit = {}) {
        directory.mkdirs()
        setup(directory)
    }

    fun Project.withDirectory(name: String, setup: File.() -> Unit = {}) {
        val directory = File(projectDir, name)
        directory.mkdirs()
        setup(directory)
    }
}

fun Project.applyAndroidApplication() {
    plugins.apply("com.android.application")
    plugins.apply("kotlin-android")
    extensions.getByType<AppExtension>().run {
        compileSdkVersion(29)
    }
}