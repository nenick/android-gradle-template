package de.nenick.gradle.plugins.tasks

import de.nenick.gradle.test.tools.TaskTest
import de.nenick.gradle.test.tools.extensions.withDirectory
import de.nenick.gradle.test.tools.extensions.withFile
import de.nenick.gradle.test.tools.taskDependenciesAsStrings
import de.nenick.gradle.test.tools.withKotlinModule
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.api.file.FileCollection
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.SourceSet
import org.gradle.kotlin.dsl.getByName
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.*

class JacocoMergeTaskTest : TaskTest<JacocoMergeTask>(JacocoMergeTask::class) {

    @Nested
    inner class Dependencies {

        @Test
        fun `depends on all project and modules jacoco report tasks`() {
            givenKotlinProject {
                plugins.apply(JacocoPlugin::class.java)
                withKotlinModule("module-alpha") { plugins.apply(JacocoPlugin::class.java) }
                withKotlinModule("module-beta") { plugins.apply(JacocoPlugin::class.java) }
            }

            expectThat(taskUnderTest.taskDependenciesAsStrings) {
                hasSize(3)
                contains("task ':jacocoTestReport'")
                contains("task ':module-alpha:jacocoTestReport'")
                contains("task ':module-beta:jacocoTestReport'")
            }
            expectThat(taskUnderTest.taskDependenciesAsStrings) {
                hasSize(3)
                contains("task ':jacocoTestReport'")
                contains("task ':module-alpha:jacocoTestReport'")
                contains("task ':module-beta:jacocoTestReport'")
            }
        }
    }

    @Nested
    inner class BuildSrc {
        @Test
        fun `ignore when not exist`() {
            givenKotlinProject()
            expectThat(taskUnderTest.additionalClassDirs.from).isEmpty()
        }

        @Test
        fun `include when exist`() {
            givenKotlinProject { projectDir.withDirectory("buildSrc") }
            expectThat(taskUnderTest.additionalSourceDirs.from).one {
                isA<FileCollection>().get { asPath }.isEqualTo("${project.projectDir}/buildSrc/src/main/kotlin")
            }
            expectThat(taskUnderTest.additionalClassDirs.from).one {
                isA<ConfigurableFileTree>().get { dir.path }.isEqualTo("${project.projectDir}/buildSrc/build/classes/kotlin/main")
            }
        }

        @Test
        fun `filter $$inlined from class directories`() {
            givenKotlinProject {
                projectDir.withDirectory("/buildSrc/build/classes/kotlin/main") {
                    withFile("UseInlined.class")
                    withFile("UseInlined\$\$inlined.class")
                }
            }
            expectThat(taskUnderTest.additionalClassDirs.files.map { it.name }) {
                hasSize(1)
                contains("UseInlined.class")
                doesNotContain("UseInlined\$\$inlined.class")
            }
        }
    }

    @Nested
    inner class Configuration {

        @Test
        fun `adjust html report`() {
            givenKotlinProject()
            taskUnderTest.configureReports()

            expectThat(project.tasks.getByName<JacocoMergeTask>("task").reports.html) {
                get { isEnabled }.isTrue()
                get { destination.path }.isEqualTo("${project.projectDir}/build/reports/jacoco/merged/html")
            }
        }

        @Test
        fun `adjust xml report`() {
            givenKotlinProject()
            taskUnderTest.configureReports()

            expectThat(taskUnderTest.reports.xml) {
                get { isEnabled }.isTrue()
                get { destination.path }.isEqualTo("${project.projectDir}/build/reports/jacoco/merged/jacocoTestReport.xml")
            }
        }
    }
}