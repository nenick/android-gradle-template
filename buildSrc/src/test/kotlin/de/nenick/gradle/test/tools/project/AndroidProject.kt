package de.nenick.gradle.test.tools.project

import com.android.build.gradle.BaseExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.testfixtures.ProjectBuilder
import java.io.File
import java.util.*

class AndroidProject(type: AndroidType = AndroidType.Application, projectDefinition: ProjectBuilder.() -> Unit = {}) :
    ProjectSetup<AndroidProject>(projectDefinition) {

    enum class AndroidType {
        Application, Library
    }

    init {
        when (type) {
            AndroidType.Application -> withPlugin("com.android.application")
            AndroidType.Library -> withPlugin("com.android.library")
        }
        withPlugin("kotlin-android")
        androidExtension {
            compileSdkVersion(29)
        }
    }

    fun androidExtension(block: BaseExtension.() -> Unit) = block(project.extensions.getByType())

    /**
     * Evaluate the project.
     *
     * Variants and depending tasks are only created after project evaluation.
     */
    // TODO find the reason why some tests are able to access variant tasks without calling this method.
    fun forceCreateVariantsAndTasks() {
        provideAndroidSdkPath()
        // When we call AppPlugin.variantManager.createVariantsAndTasks() directly it will fail
        // later because of adding duplicated task when calling methods like getTasksByName.
        // But we need createVariantsAndTasks() called to have access to all variants ands tasks.
        // TODO find a trigger which makes more sense.
        project.getTasksByName("test", true)
    }

    private fun provideAndroidSdkPath() {
        val localProperties = File("../local.properties")
        if (localProperties.exists()) {
            val properties = Properties()
            properties.load(localProperties.inputStream())
            val sdkDir = properties.getProperty("sdk.dir")
            rootProject.withFile("local.properties") { writeText("sdk.dir=$sdkDir") }
        }
    }
}