package de.nenick.gradle.plugins

import de.nenick.gradle.plugins.base.KotlinBaseTest
import de.nenick.gradle.test.tools.extensions.withDirectory
import de.nenick.gradle.test.tools.extensions.withFile
import org.gradle.api.internal.file.collections.DefaultConfigurableFileTree
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.SourceSetOutput
import org.gradle.kotlin.dsl.getByName
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.*

class KotlinModulePluginTest : KotlinBaseTest() {

    private val kotlinClassDirectory = "build/classes/kotlin/main"

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
        expectThat(jacocoReportTask().dependsOn.map { it.toString() }) {
            contains("task ':test'")
        }
    }

    @Test
    fun `replace class directory sourceSets with filtered fileTree`() {
        expectThat(jacocoReportClassDirectories().from) {
            one {
                isA<DefaultConfigurableFileTree>().and {
                    get { dir.path }.isEqualTo("${project.projectDir}/${kotlinClassDirectory}")
                }
            }
            none { isA<SourceSetOutput>() }
        }
    }

    @Test
    fun `adjust jacoco plugin to filter $$inlined from class directories`() {
        project.projectDir.withDirectory(kotlinClassDirectory) {
            withFile("UseInlined.class")
            withFile("UseInlined\$\$inlined.class")
        }
        expectThat(jacocoReportClassDirectories().files.map { it.name }) {
            hasSize(1)
            contains("UseInlined.class")
            doesNotContain("UseInlined\$\$inlined.class")
        }
    }

    private fun jacocoReportClassDirectories() = jacocoReportTask().classDirectories

    private fun jacocoReportTask() = project.tasks.getByName<JacocoReport>("jacocoTestReport")
}