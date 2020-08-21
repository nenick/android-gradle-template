package de.nenick.gradle.test.tools.project

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.getByType
import org.gradle.testfixtures.ProjectBuilder
import java.io.File
import kotlin.reflect.KClass

// The BaseClass<SubClass: BaseClass<SubClass>> is a builder pattern to return sub class instead of the base class.
// https://stackoverflow.com/questions/29565043/how-to-specify-own-type-as-return-type-in-kotlin
abstract class ProjectSetup<SubClass : ProjectSetup<SubClass>>(
    projectDefinition: ProjectBuilder.() -> Unit = {},
    private val project: Project = ProjectBuilder.builder().apply(projectDefinition).build()
) : Project by project {

    // Like kotlin apply, because super class has apply method which hides the kotlin variant.
    fun setup(block: SubClass.() -> Unit): SubClass {
        // Here we have to cast, because the compiler does not know that this is an instance of SubClass.
        @Suppress("UNCHECKED_CAST") val asSubClass = this as SubClass
        block(asSubClass)
        return asSubClass
    }

    fun withPlugin(type: String) = setup { project.plugins.apply(type) }
    fun <T : Plugin<*>> withPlugin(type: KClass<T>) = setup { project.plugins.apply(type.java) }
    fun withRawModule(name: String, block: RawProject.() -> Unit = {}): RawProject {
        return RawProject {
            withName(name)
            withParent(project)
        }.apply(block)
    }

    fun <T : Task> withTask(name: String, task: KClass<T>, block: T.() -> Unit = {}) = setup { block(tasks.register(name, task.java).get()) }

    fun withKotlinModule(name: String, block: KotlinProject.() -> Unit = {}): KotlinProject {
        return KotlinProject {
            withName(name)
            withParent(project)
        }.apply(block)
    }

    fun withAndroidModule(name: String, block: AndroidProject.() -> Unit = {}): AndroidProject {
        return AndroidProject {
            withName(name)
            withParent(project)
        }.apply(block)
    }

    fun withDirectory(directory: File, setup: File.() -> Unit = {}) = setup {
        directory.mkdirs()
        setup(directory)
    }

    fun withDirectory(name: String, setup: File.() -> Unit = {}) = setup {
        val directory = File(projectDir, name)
        directory.mkdirs()
        setup(directory)
    }
}