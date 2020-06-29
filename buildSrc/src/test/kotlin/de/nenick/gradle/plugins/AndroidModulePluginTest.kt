package de.nenick.gradle.plugins

import com.android.build.gradle.AppExtension
import de.nenick.gradle.plugins.base.KotlinBaseTest
import org.gradle.kotlin.dsl.getByType
import org.gradle.testfixtures.ProjectBuilder
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isTrue

class AndroidModulePluginTest : KotlinBaseTest() {

    override val project = ProjectBuilder.builder().build().also {
        it.plugins.apply("com.android.application")
        it.extensions.getByType<AppExtension>().run {
            compileSdkVersion(29)
        }
        it.plugins.apply("nenick-android-module")
    }!!

    @Nested
    inner class AdjustKtlintPlugin {

        @Test
        fun `enable android specific checks`() {
            expectThat(project.extensions.getByType<KtlintExtension>().android.get()).isTrue()
        }
    }
}