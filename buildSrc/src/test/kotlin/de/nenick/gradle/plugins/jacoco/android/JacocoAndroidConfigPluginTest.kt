package de.nenick.gradle.plugins.jacoco.android

import de.nenick.gradle.test.tools.PluginTest
import de.nenick.gradle.test.tools.project.AndroidProject
import de.nenick.gradle.test.tools.project.AndroidProject.AndroidType
import de.nenick.gradle.test.tools.project.KotlinProject
import de.nenick.gradle.test.tools.taskDependenciesAsStrings
import org.gradle.api.internal.plugins.PluginApplicationException
import org.gradle.kotlin.dsl.getByType
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.*

class JacocoAndroidConfigPluginTest : PluginTest<AndroidProject>() {

    private val pluginId = "de.nenick.jacoco-android-config"

    @BeforeEach
    fun setup() {
        project = AndroidProject().withPlugin(JacocoAndroidConfigPlugin::class)
    }

    @Test
    override fun `applies by plugin id`() {
        project = AndroidProject().withPlugin(pluginId)
        expectThat(project.plugins).one { isA<JacocoAndroidConfigPlugin>() }
    }

    @Test
    fun `applies jacoco plugin`() {
        expectThat(project.plugins).one { isA<JacocoPlugin>() }
        expectThat(project.extensions.getByType<JacocoPluginExtension>().toolVersion).isEqualTo("0.8.5")
    }

    @Nested
    inner class Extension {

        @Test
        fun `add extension`() {
            expectThat(project.extensions.extensionsSchema.map { it.name }) {
                contains("jacocoAndroid")
            }
        }

        @Test
        fun `default settings`() {
            expectThat(project.extensions.getByType(JacocoAndroidExtension::class.java)) {
                get { connectedAndroidTests.skipCoverageReport }.isEqualTo(false)
                get { connectedAndroidTests.variantForCoverage }.isEqualTo("debug")
                get { androidUnitTests.skipCoverageReport }.isEqualTo(false)
                get { androidUnitTests.variantForCoverage }.isEqualTo("debug")

                // Just to create coverage for alternate access option.
                get {
                    connectedAndroidTests { }
                    androidUnitTests { }
                }
            }
        }

        @Test
        fun `reset settings with new instance`() {
            val instanceA = JacocoAndroidExtension()
            expectThat(instanceA.androidUnitTests.variantForCoverage).isEqualTo("debug")
            instanceA.androidUnitTests.variantForCoverage = "other"

            val instanceB = JacocoAndroidExtension()
            expectThat(instanceB.androidUnitTests.variantForCoverage).isEqualTo("debug")
        }
    }

    @Nested
    inner class JacocoTestReportTask {

        @Test
        fun `add the default jacoco report task`() {
            expectThat(project.tasks.matching { true }.map { it.name }) {
                contains("jacocoTestReport")
            }
        }

        @Test
        @Disabled("just a test that all tasks are generated, related code ist commented out")
        fun `jacoco report task depends on connected android tests`() {
            expectThat(jacocoReportTask().taskDependenciesAsStrings) {
                contains("task ':connectedDebugAndroidTest'")
            }
        }

        @Test
        fun `apply plugin to pure kotlin project`() {
            // Within pure kotlin projects jacoco create his own jacocoTestReport task automatically.
            expectThrows<PluginApplicationException> { KotlinProject().setup { plugins.apply(pluginId) } }
                .message.isEqualTo("Failed to apply plugin [id '$pluginId']")
        }
    }

    @Nested
    inner class ConnectedAndroidTests {

        @Nested
        inner class Application {

            @BeforeEach
            fun setup() {
                project = AndroidProject(AndroidType.Application).withPlugin(JacocoAndroidConfigPlugin::class)
            }

            @Test
            fun `add the default connect android tests report task`() {
                project.forceCreateVariantsAndTasks()
                expectThat(project.tasks) {
                    one {
                        get { name }.isEqualTo("jacocoDebugConnectedTestReport")
                        isA<JacocoConnectedAndroidTestReport>()
                    }
                }
            }
        }

        @Nested
        inner class Library {

            @BeforeEach
            fun setup() {
                project = AndroidProject(AndroidType.Library).withPlugin(JacocoAndroidConfigPlugin::class)
            }

            @Test
            fun `add the default connect android tests report task`() {
                project.forceCreateVariantsAndTasks()
                expectThat(project.tasks) {
                    one {
                        get { name }.isEqualTo("jacocoDebugConnectedTestReport")
                        isA<JacocoConnectedAndroidTestReport>()
                    }
                }
            }
        }
    }

    @Nested
    inner class AndroidUnitTests {

        @Nested
        inner class Application {

            @BeforeEach
            fun setup() {
                project = AndroidProject(AndroidType.Application).withPlugin(JacocoAndroidConfigPlugin::class)
            }

            @Test
            fun `add the default android unit tests report task`() {
                project.forceCreateVariantsAndTasks()
                expectThat(project.tasks) {
                    one {
                        get { name }.isEqualTo("jacocoDebugUnitTestReport")
                        isA<JacocoAndroidUnitTestReport>()
                    }
                }
            }
        }

        @Nested
        inner class Library {

            @BeforeEach
            fun setup() {
                project = AndroidProject(AndroidType.Library).withPlugin(JacocoAndroidConfigPlugin::class)
            }

            @Test
            fun `add the default android unit tests report task`() {
                project.forceCreateVariantsAndTasks()
                expectThat(project.tasks) {
                    one {
                        get { name }.isEqualTo("jacocoDebugUnitTestReport")
                        isA<JacocoAndroidUnitTestReport>()
                    }
                }
            }
        }
    }

    private fun jacocoReportTask() = project.tasks.getByName("jacocoTestReport")
}