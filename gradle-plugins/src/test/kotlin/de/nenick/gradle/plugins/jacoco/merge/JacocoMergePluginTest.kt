package de.nenick.gradle.plugins.jacoco.merge

import de.nenick.gradle.test.tools.PluginTest
import de.nenick.gradle.test.tools.project.RawProject
import de.nenick.gradle.test.tools.taskDependenciesAsStrings
import org.gradle.kotlin.dsl.getByName
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.*

class JacocoMergePluginTest : PluginTest<RawProject>() {

    private val pluginId = "de.nenick.jacoco-merge"
    private val mergeTaskName = "jacocoTestReportMerge"
    private val htmlReportDir = "reports/jacoco/merged/html"
    private val xmlReportDir = "reports/jacoco/merged/jacocoTestReport.xml"

    @BeforeEach
    fun setup() {
        project = RawProject().withPlugin(JacocoMergePlugin::class)
    }

    @Test
    override fun `applies by plugin id`() {
        project = RawProject().setup { withPlugin(pluginId) }
        expectThat(project.plugins).one { isA<JacocoMergePlugin>() }
    }

    @Test
    fun `applies jacoco plugin`() {
        expectThat(project.plugins).one { isA<JacocoPlugin>() }
    }

    @Test
    fun `adds jacoco merge task`() {
        expectThat(project.tasks).one {
            isA<JacocoMergeTask>()
            get { name }.isEqualTo(mergeTaskName)
        }
    }

    @Nested
    inner class MergeTaskConfiguration {
        @Test
        fun `depends on all jacoco report tasks`() {
            project = RawProject().setup {
                withKotlinModule("module-alpha") { withPlugin(JacocoPlugin::class) }
                withKotlinModule("module-beta") { withPlugin(JacocoPlugin::class) }
                withPlugin(JacocoMergePlugin::class)
            }
            expectThat(jacocoMergeTask().taskDependenciesAsStrings) {
                hasSize(2)
                contains("task ':module-alpha:jacocoTestReport'")
                contains("task ':module-beta:jacocoTestReport'")
            }
        }

        @Test
        fun `html report dir`() {
            expectThat(jacocoMergeTask().reports.html) {
                get { isEnabled }.isTrue()
                get { destination.path }.isEqualTo("${project.buildDir}/$htmlReportDir")
            }
        }

        @Test
        fun `xml report file`() {
            expectThat(jacocoMergeTask().reports.xml) {
                get { isEnabled }.isTrue()
                get { destination.path }.isEqualTo("${project.buildDir}/$xmlReportDir")
            }
        }
    }

    private fun jacocoMergeTask() = project.tasks.getByName<JacocoMergeTask>(mergeTaskName)
}