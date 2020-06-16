package de.nenick.gradle.plugins.basics

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.register
import org.gradle.testfixtures.ProjectBuilder
import kotlin.reflect.KClass

abstract class TaskTest<T : Task>(
    private val taskClass: KClass<T>
) {
    lateinit var project: Project
    lateinit var task: T

    fun whenRunTask() {
        task.taskDependencies.getDependencies(task).forEach { dependency ->
            dependency.actions.forEach { it.execute(dependency) }
        }
        task.actions.forEach { it.execute(task) }
    }

    fun givenProject(setup: Project.() -> Unit = {}) {
        project = ProjectBuilder.builder().build()
        setup(project)
        task = project.tasks.register("task", taskClass).get()
    }

    fun givenJavaProject(setup: Project.() -> Unit) {
        givenProject {
            setup(this)
            plugins.apply("java")
        }
    }
}
