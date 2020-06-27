package de.nenick.gradle.plugins

import com.android.build.gradle.AppExtension
import de.nenick.gradle.plugins.base.BaseKotlinModulePluginTest
import org.gradle.kotlin.dsl.getByType
import org.gradle.testfixtures.ProjectBuilder
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.junit.Test
import strikt.api.expectThat

class AndroidModulePluginTest : BaseKotlinModulePluginTest() {

    override val project = ProjectBuilder.builder().build().also {
        it.plugins.apply("com.android.application")
        it.extensions.getByType<AppExtension>().run {
            compileSdkVersion(29)
        }
        it.plugins.apply("nenick-android-module")
    }!!

    @Test
    fun `adjusts ktlint plugin`() {
        expectThat(project.extensions.getByType<KtlintExtension>()) {
            assertThat("android specific checks enabled") { it.android.get() }
        }
    }
}