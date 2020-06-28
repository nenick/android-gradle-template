package de.nenick.gradle.plugins

import de.nenick.gradle.plugins.base.KotlinBasedModulePluginTest
import de.nenick.gradle.test.tools.extensions.withDirectory
import de.nenick.gradle.test.tools.extensions.withFile
import org.gradle.api.plugins.JavaPlugin
import org.gradle.kotlin.dsl.getByName
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isA
import strikt.assertions.one

class KotlinModulePluginTest : KotlinBasedModulePluginTest() {

    override val project = ProjectBuilder.builder().build().also {
        it.plugins.apply("nenick-kotlin-module")
    }!!

    @Test
    fun `adds base kotlin project plugins`() {
        expectThat(project.plugins) {
            one { isA<JavaPlugin>() }
            one { isA<KotlinModulePlugin>() }
        }
    }

    @Test
    fun `adjust jacoco plugin to depend on test`() {
        expectThat(project.tasks.getByName<JacocoReport>("jacocoTestReport")).get {
            expectThat(dependsOn.map { it.toString() }).contains("task ':test'")
        }
    }

    @Test
    fun `adjust jacoco plugin to filter $$inlined from class directories`() {
        project.projectDir.withDirectory("build/classes/kotlin/main") {
            withFile("UseInlined.class")
            withFile("UseInlined\$\$inlined.class")
        }
        expectThat(project.tasks.getByName<JacocoReport>("jacocoTestReport")).get {
            expectThat(classDirectories.files.toString()).contains("UseInlined.class")
            expectThat(classDirectories.files.toString()).not { contains("UseInlined\$\$inlined.class") }
        }
    }


}