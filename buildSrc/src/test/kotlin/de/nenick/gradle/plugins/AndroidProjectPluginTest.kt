package de.nenick.gradle.plugins

import de.nenick.gradle.plugins.basics.hasGroup
import de.nenick.gradle.plugins.basics.hasName
import de.nenick.gradle.plugins.tasks.CleanOutputTestTask
import de.nenick.gradle.plugins.tasks.CleanTask
import de.nenick.gradle.plugins.tasks.KtlintOutputTestTask
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test
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
    fun `adds clean-check tasks`() {
        expectThat(project.tasks).one {
            isA<CleanOutputTestTask>()
            hasName("clean-test")
            hasGroup("output tests")
        }
    }

    @Test
    fun `adds ktLint-check tasks`() {
        expectThat(project.tasks).one {
            isA<KtlintOutputTestTask>()
            hasName("ktlint-test")
            hasGroup("output tests")
        }
    }
}