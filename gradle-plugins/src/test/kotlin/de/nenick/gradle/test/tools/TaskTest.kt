package de.nenick.gradle.test.tools

import de.nenick.gradle.test.tools.project.ProjectSetup
import org.gradle.api.Task
import org.gradle.kotlin.dsl.register
import kotlin.reflect.KClass

abstract class TaskTest<T : Task, P : ProjectSetup<P>>(
    private val taskClass: KClass<T>
) {

    lateinit var project: P
    lateinit var taskUnderTest: T

    fun whenRunTaskActions() {
        taskUnderTest.actions.forEach { it.execute(taskUnderTest) }
    }

    fun ProjectSetup<*>.withTaskUnderTest() = setup {
        taskUnderTest = tasks.register("task", taskClass).get()
    }
}