package de.nenick.gradle.plugins

import de.nenick.gradle.test.tools.hasGroup
import de.nenick.gradle.test.tools.hasName
import de.nenick.gradle.plugins.tasks.output.CleanOutputTask
import de.nenick.gradle.plugins.tasks.CleanTask
import de.nenick.gradle.plugins.tasks.output.JacocoOutputTask
import de.nenick.gradle.plugins.tasks.output.KtlintOutputTask
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isA
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