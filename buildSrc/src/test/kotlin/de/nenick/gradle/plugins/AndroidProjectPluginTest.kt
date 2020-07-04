package de.nenick.gradle.plugins

import de.nenick.gradle.plugins.tasks.CleanTask
import de.nenick.gradle.plugins.tasks.JacocoMergeTask
import de.nenick.gradle.plugins.tasks.output.CleanOutputTask
import de.nenick.gradle.plugins.tasks.output.JacocoOutputTask
import de.nenick.gradle.plugins.tasks.output.KtlintOutputTask
import de.nenick.gradle.test.tools.hasGroup
import de.nenick.gradle.test.tools.hasName
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.one

class AndroidProjectPluginTest {

    private val project = ProjectBuilder.builder().build().also {
        it.pluginManager.apply("nenick-android-project")
    }

    @Test
    fun `plugin id loads correct class`() {
        expectThat(project.plugins).one { isA<AndroidProjectPlugin>() }
    }

    @Test
    fun `adds clean task`() {
        expectThat(project.tasks).one {
            isA<CleanTask>()
            hasName("clean")
            hasGroup("cleanup")
        }
    }


    @Test
    fun `adds jacoco merge task`() {
        expectThat(project.plugins).one { isA<JacocoPlugin>() }
        expectThat(project.tasks).one {
            isA<JacocoMergeTask>().and {
                get { name }.isEqualTo("jacocoTestReportMerge")
                get { reports.html.destination.path }.isEqualTo("${project.buildDir}/reports/jacoco/merged/html")
            }
        }
    }

    @Nested
    inner class OutputTestTasks {

        @Test
        fun `adds clean-test tasks`() {
            expectThat(project.tasks).one {
                isA<CleanOutputTask>()
                hasName("clean-test")
                hasGroup("output tests")
            }
        }

        @Test
        fun `adds ktLint-test tasks`() {
            expectThat(project.tasks).one {
                isA<KtlintOutputTask>()
                hasName("ktlint-test")
                hasGroup("output tests")
            }
        }

        @Test
        fun `adds jacoco-test tasks`() {
            expectThat(project.tasks).one {
                isA<JacocoOutputTask>()
                hasName("jacoco-test")
                hasGroup("output tests")
            }
        }
    }
}