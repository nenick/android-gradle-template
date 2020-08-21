package de.nenick.gradle.test.tools.project

import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.plugins.AppPlugin
import de.nenick.gradle.test.tools.withFile
import org.gradle.kotlin.dsl.getByType
import org.gradle.testfixtures.ProjectBuilder
import java.io.File
import java.util.*

class AndroidProject(projectDefinition: ProjectBuilder.() -> Unit = {}) : ProjectSetup<AndroidProject>(projectDefinition) {

    init {
        withPlugin("com.android.application")
        withPlugin("kotlin-android")
        project.extensions.getByType<AppExtension>().run {
            compileSdkVersion(29)
        }
        provideAndroidSdkPath()
        // When we call AppPlugin.variantManager.createVariantsAndTasks() directly it will fail
        // later because of adding duplicated task when calling methods like getTasksByName.
        // But we need createVariantsAndTasks() called to have access to all variants ands tasks.
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