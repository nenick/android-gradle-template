package de.nenick.gradle.plugins.jacoco.merge

import de.nenick.gradle.test.tools.TaskTest
import de.nenick.gradle.test.tools.extensions.withDirectory
import de.nenick.gradle.test.tools.extensions.withFile
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.api.file.FileCollection
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.*

class JacocoMergeTaskTest : TaskTest<JacocoMergeTask>(JacocoMergeTask::class) {
    @Nested
    inner class BuildSrc {
        @Test
        fun `ignore when not exist`() {
            givenKotlinProject()
            expectThat(taskUnderTest.additionalClassDirs.from).isEmpty()
        }

        @Test
        fun `include when exist`() {
            givenKotlinProject { withDirectory("buildSrc") }
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
                withDirectory("buildSrc/build/classes/kotlin/main") {
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
}