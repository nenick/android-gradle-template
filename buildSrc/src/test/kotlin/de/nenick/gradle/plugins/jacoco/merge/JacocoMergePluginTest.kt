package de.nenick.gradle.plugins.jacoco.merge

import de.nenick.gradle.test.tools.PluginTest
import de.nenick.gradle.test.tools.taskDependenciesAsStrings
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByName
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.*


class JacocoMergePluginTest : PluginTest() {
    private val pluginId = "de.nenick.jacoco-merge"
    private val mergeTaskName = "jacocoTestReportMerge"
    private val htmlReportDir = "reports/jacoco/merged/html"
    private val xmlReportDir = "reports/jacoco/merged/jacocoTestReport.xml"

    @Test
    override fun `applies by plugin id`() {
        givenEmptyProject { plugins.apply(pluginId) }
        expectThat(project.plugins).one { isA<JacocoMergePlugin>() }
    }

    @Test
    fun `applies jacoco plugin`() {
        givenEmptyProjectWithPluginApplied()
        expectThat(project.plugins).one { isA<JacocoPlugin>() }
    }

    @Test
    fun `adds jacoco merge task`() {
        givenEmptyProjectWithPluginApplied()
        expectThat(project.tasks).one {
            isA<JacocoMergeTask>()
            get { name }.isEqualTo(mergeTaskName)
        }
    }

    @Nested
    inner class MergeTaskConfiguration {
        @Test
        fun `depends on all jacoco report tasks`() {
            givenKotlinProject {
                withKotlinModule("module-alpha") { plugins.apply(JacocoPlugin::class.java) }
                withKotlinModule("module-beta") { plugins.apply(JacocoPlugin::class.java) }
                plugins.apply(JacocoMergePlugin::class.java)
            }
            expectThat(jacocoMergeTask().taskDependenciesAsStrings) {
                hasSize(3)
                contains("task ':jacocoTestReport'")
                contains("task ':module-alpha:jacocoTestReport'")
                contains("task ':module-beta:jacocoTestReport'")
            }
        }

        @Test
        fun `html report dir`() {
            givenEmptyProjectWithPluginApplied()
            expectThat(jacocoMergeTask().reports.html) {
                get { isEnabled }.isTrue()
                get { destination.path }.isEqualTo("${project.buildDir}/${htmlReportDir}")
            }
        }

        @Test
        fun `xml report file`() {
            givenEmptyProjectWithPluginApplied()
            expectThat(jacocoMergeTask().reports.xml) {
                get { isEnabled }.isTrue()
                get { destination.path }.isEqualTo("${project.buildDir}/${xmlReportDir}")
            }
        }
    }

    override fun givenEmptyProjectWithPluginApplied(setup: Project.() -> Unit) {
        givenEmptyProject { plugins.apply(JacocoMergePlugin::class.java) }
        setup(project)
    }

    private fun jacocoMergeTask() = project.tasks.getByName<JacocoMergeTask>(mergeTaskName)
}