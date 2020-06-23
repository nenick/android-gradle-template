package de.nenick.gradle.plugins.basics

import com.android.build.gradle.AppExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
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
    project.applyAndroidApplication()
    setup(project)
}

fun Project.applyAndroidApplication() {
    plugins.apply("com.android.application")
    plugins.apply("kotlin-android")
    extensions.getByType<AppExtension>().run {
        compileSdkVersion(29)
    }
}