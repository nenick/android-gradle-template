package de.nenick.gradle.plugins.jacoco.kotlin

import de.nenick.gradle.test.tools.PluginTest
import de.nenick.gradle.test.tools.extensions.withDirectory
import de.nenick.gradle.test.tools.extensions.withFile
import org.gradle.api.Project
import org.gradle.api.internal.file.collections.DefaultConfigurableFileTree
import org.gradle.api.tasks.SourceSetOutput
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.getByType
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.*

class JacocoKotlinConfigPluginTest : PluginTest() {
    private val pluginId = "de.nenick.jacoco-kotlin-config"
    private val kotlinClassDirectory = "build/classes/kotlin/main"

    @Test
    override fun `applies by plugin id`() {
        givenKotlinProject { plugins.apply(pluginId) }
        expectThat(project.plugins).one { isA<JacocoKotlinConfigPlugin>() }
    }

    @Test
    fun `applies jacoco plugin`() {
        givenKotlinProjectWithPluginApplied()
        expectThat(project.plugins).one { isA<JacocoPlugin>() }
        expectThat(project.extensions.getByType<JacocoPluginExtension>().toolVersion).isEqualTo("0.8.5")
    }

    @Test
    fun `adjust jacoco plugin to depend on test`() {
        givenKotlinProjectWithPluginApplied()
        expectThat(jacocoReportTask().dependsOn.map { it.toString() }) {
            contains("task ':test'")
        }
    }

    @Test
    fun `replace class directory sourceSets with filtered fileTree`() {
        givenKotlinProjectWithPluginApplied()
        expectThat(jacocoReportClassDirectories().from) {
            one {
                isA<DefaultConfigurableFileTree>().and {
                    get { dir.path }.isEqualTo("${project.projectDir}/$kotlinClassDirectory")
                }
            }
            none { isA<SourceSetOutput>() }
        }
    }

    @Test
    fun `adjust jacoco plugin to filter $$inlined from class directories`() {
        givenKotlinProjectWithPluginApplied {
            withDirectory(kotlinClassDirectory) {
                withFile("UseInlined.class")
                withFile("UseInlined\$\$inlined.class")
            }
        }
        expectThat(jacocoReportClassDirectories().files.map { it.name }) {
            hasSize(1)
            contains("UseInlined.class")
            doesNotContain("UseInlined\$\$inlined.class")
        }
    }

    private fun jacocoReportClassDirectories() = jacocoReportTask().classDirectories

    private fun jacocoReportTask() = project.tasks.getByName<JacocoReport>("jacocoTestReport")

    override fun givenEmptyProjectWithPluginApplied(setup: Project.() -> Unit) {
        givenEmptyProject { plugins.apply(JacocoKotlinConfigPlugin::class.java) }
    }

    private fun givenKotlinProjectWithPluginApplied(setup: Project.() -> Unit = {}) {
        givenKotlinProject {
            plugins.apply(JacocoKotlinConfigPlugin::class.java)
        }
        setup(project)
    }
}