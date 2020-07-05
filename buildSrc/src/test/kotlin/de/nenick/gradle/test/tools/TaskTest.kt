package de.nenick.gradle.test.tools

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.register
import kotlin.reflect.KClass

abstract class TaskTest<T : Task>(
    private val taskClass: KClass<T>
) : ProjectExtensions {
    override lateinit var project: Project
    lateinit var taskUnderTest: T

    fun whenRunTaskActions() {
        taskUnderTest.actions.forEach { it.execute(taskUnderTest) }
    }

    override fun givenEmptyProject(setup: Project.() -> Unit): Project {
        return super.givenEmptyProject(setup).also {
            it.withTaskUnderTest()
        }
    }

    private fun Project.withTaskUnderTest() {
        taskUnderTest = tasks.register("task", taskClass).get()
    }
}