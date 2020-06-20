package de.nenick.gradle.plugins.basics

import kotlin.reflect.KClass
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.register
import org.gradle.testfixtures.ProjectBuilder

abstract class TaskTest<T : Task>(
    private val taskClass: KClass<T>
) {
    lateinit var project: Project
    lateinit var taskUnderTest: T

    fun whenRunTask() {
        taskUnderTest.actions.forEach { it.execute(taskUnderTest) }
    }

    fun givenEmptyProject(setup: Project.() -> Unit = {}) {
        project = ProjectBuilder.builder().build()
        setup(project)
        taskUnderTest = project.tasks.register("task", taskClass).get()
    }

    fun givenKotlinProject(setup: Project.() -> Unit = {}) {
        givenEmptyProject {
            plugins.apply("kotlin")
            setup(this)
        }
    }
}