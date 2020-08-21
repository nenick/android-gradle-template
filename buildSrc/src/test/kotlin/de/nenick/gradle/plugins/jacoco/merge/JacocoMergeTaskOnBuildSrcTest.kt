package de.nenick.gradle.plugins.jacoco.merge

import de.nenick.gradle.test.tools.TaskTest
import de.nenick.gradle.test.tools.extensions.withFile
import de.nenick.gradle.test.tools.project.RawProject
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.api.file.FileCollection
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.*

class JacocoMergeTaskOnBuildSrcTest : TaskTest<JacocoMergeTask, RawProject>(JacocoMergeTask::class) {

    @BeforeEach
    fun setup() {
        project = RawProject().setup { withTaskUnderTest() }
    }

    @Test
    fun `ignore when not exist`() {
        expectThat(taskUnderTest.additionalClassDirs.from).isEmpty()
    }

    @Test
    fun `include when exist`() {
        project = RawProject().setup {
            withDirectory("buildSrc")
            withTaskUnderTest()
        }
        expectThat(taskUnderTest.additionalSourceDirs.from).one {
            isA<FileCollection>().get { asPath }.isEqualTo("${project.projectDir}/buildSrc/src/main/java")
        }
        expectThat(taskUnderTest.additionalClassDirs.from).one {
            isA<ConfigurableFileTree>().get { dir.path }.isEqualTo("${project.projectDir}/buildSrc/build/classes/kotlin/main")
        }
    }

    @Test
    fun `filter $$inlined from class directories`() {
        project = RawProject().setup {
            withDirectory("buildSrc/build/classes/kotlin/main") {
                withFile("UseInlined.class")
                withFile("UseInlined\$\$inlined.class")
            }
            withTaskUnderTest()
        }
        expectThat(taskUnderTest.additionalClassDirs.files.map { it.name }) {
            hasSize(1)
            contains("UseInlined.class")
            doesNotContain("UseInlined\$\$inlined.class")
        }
    }
}