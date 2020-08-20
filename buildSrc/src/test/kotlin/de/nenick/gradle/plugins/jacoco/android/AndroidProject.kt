package de.nenick.gradle.plugins.jacoco.android

import com.android.build.gradle.internal.plugins.AppPlugin
import de.nenick.gradle.test.tools.withFile
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import java.io.File
import java.util.*
import kotlin.reflect.KClass

class AndroidProject(
    private val project: Project = ProjectBuilder.builder().build(),
    setup: AndroidProject.() -> Unit = {}
) : Project by project {

    init {
        withPlugin("com.android.application")
        createAndroidVariantsAndTasks()
    }

    fun <T : Plugin<*>> withPlugin(type: KClass<T>) = also { project.plugins.apply(type.java) }
    fun withPlugin(type: String) = also { project.plugins.apply(type) }
    fun withKotlinPlugin() = withPlugin("kotlin-android")
    fun build() = project

    private fun createAndroidVariantsAndTasks() {
        val localProperties = File("../local.properties")
        if (localProperties.exists()) {
            val properties = Properties()
            properties.load(localProperties.inputStream())
            val sdkDir = properties.getProperty("sdk.dir")
            project.withFile("local.properties") { writeText("sdk.dir=$sdkDir") }
        }
        project.plugins.findPlugin(AppPlugin::class.java)!!.apply {
            variantManager.createVariantsAndTasks()
        }
    }
}