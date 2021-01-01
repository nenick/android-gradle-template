package de.nenick.gradle.plugins.jacoco.android

import de.nenick.gradle.test.tools.PluginTest
import de.nenick.gradle.test.tools.extensions.withFile
import de.nenick.gradle.test.tools.project.RawProject
import de.nenick.gradle.test.tools.project.AndroidProject
import de.nenick.gradle.test.tools.project.AndroidProject.AndroidType
import de.nenick.gradle.test.tools.project.KotlinProject
import de.nenick.gradle.tools.reporting
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.api.file.FileCollection
import org.gradle.api.internal.plugins.PluginApplicationException
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.getByType
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.*
import java.io.File

@Suppress("ClassName")
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
            expectThat(jacocoAndroidExtension()) {
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

    @Test
    fun `apply plugin to pure kotlin project`() {
        // Within pure kotlin projects jacoco create his own jacocoTestReport task automatically.
        expectThrows<PluginApplicationException> { KotlinProject().setup { plugins.apply(pluginId) } }
            .message.isEqualTo("Failed to apply plugin '$pluginId'.")
    }

    @Nested
    inner class `parent task to combine` {

        @Test
        fun `is added`() {
            expectThat(project.tasks.matching { true }.map { it.name }) {
                contains("jacocoTestReport")
            }
        }

        @Test
        fun `depends on connect and unit tests report tasks`() {
            project.forceCreateVariantsAndTasks()
            expectThat(jacocoReportTask().taskDependencies.getDependencies(jacocoReportTask())) {
                one { get { name }.isEqualTo("jacocoDebugConnectedTestReport") }
                one { get { name }.isEqualTo("jacocoDebugUnitTestReport") }
            }
        }
    }

    @Nested
    inner class `specific report tasks` {

        @Test
        fun `include java source files`() {
            project.forceCreateVariantsAndTasks()
            expectThat(jacocoConnectedTestReportTask().additionalSourceDirs.from.flatMap { (it as FileCollection).files })
                .containsExactly(
                    File(project.projectDir, "src/main/java"),
                    File(project.projectDir, "src/debug/java")
                )
        }

        @Test
        fun `include java source files from custom variants`() {
            project.androidExtension {
                buildTypes.create("type")
                productFlavors.create("flavour") { flavorDimensions("product") }
            }
            jacocoAndroidExtension().connectedAndroidTests.variantForCoverage = "flavourType"
            project.forceCreateVariantsAndTasks()
            expectThat(project.tasks) { one { get { name }.isEqualTo("jacocoFlavourTypeConnectedTestReport") } }
            expectThat(jacocoConnectedTestReportTask().additionalSourceDirs.from.flatMap { (it as FileCollection).files })
                .containsExactly(
                    File(project.projectDir, "src/main/java"),
                    File(project.projectDir, "src/flavour/java"),
                    File(project.projectDir, "src/type/java"),
                    File(project.projectDir, "src/flavourType/java")
                )
        }

        @Test
        fun `include class files`() {
            project.forceCreateVariantsAndTasks()
            expectThat(jacocoConnectedTestReportTask().additionalClassDirs.from.map { (it as ConfigurableFileTree).dir })
                .containsExactly(
                    File(project.projectDir, "build/intermediates/javac/debug/classes"),
                    File(project.projectDir, "build/tmp/kotlin-classes/debug")
                )
        }

        @Test
        fun `filter java class files`() {
            project.withDirectory("build/intermediates/javac/debug/classes") {
                withFile("BuildConfig.class")
                withFile("Other.class")
            }
            project.forceCreateVariantsAndTasks()
            expectThat(jacocoConnectedTestReportTask().additionalClassDirs.from.flatMap { (it as ConfigurableFileTree).files })
                .containsExactly(File(project.projectDir, "build/intermediates/javac/debug/classes/Other.class"))
        }

        @Test
        fun `filter kotlin class files`() {
            project.withDirectory("build/tmp/kotlin-classes/debug") {
                withFile("UseInlined.class")
                withFile("UseInlined\$\$inlined.class")
            }
            project.forceCreateVariantsAndTasks()
            expectThat(jacocoConnectedTestReportTask().additionalClassDirs.from.flatMap { (it as ConfigurableFileTree).files })
                .containsExactly(File(project.projectDir, "build/tmp/kotlin-classes/debug/UseInlined.class"))
        }

        @Nested
        inner class `for connected android tests` {

            @Test
            fun `produce html report`() {
                project.forceCreateVariantsAndTasks()
                expectThat(jacocoConnectedTestReportTask().reports.html) {
                    get { isEnabled }.isTrue()
                    get { destination }.isEqualTo(project.reporting.file("jacoco/connectedDebug/html"))
                }
            }

            @Test
            fun `xml report can be activated`() {
                project.forceCreateVariantsAndTasks()
                expectThat(jacocoConnectedTestReportTask().reports.xml.isEnabled).isFalse()
                jacocoConnectedTestReportTask().reports.xml.isEnabled = true
                expectThat(jacocoConnectedTestReportTask().reports.xml) {
                    get { isEnabled }.isTrue()
                    get { destination }
                        .isEqualTo(project.reporting.file("jacoco/jacocoDebugConnectedTestReport/jacocoDebugConnectedTestReport.xml"))
                }
            }

            @Test
            fun `depend on connected android test execution`() {
                project.forceCreateVariantsAndTasks()
                expectThat(jacocoConnectedTestReportTask().taskDependencies.getDependencies(jacocoConnectedTestReportTask())) {
                    one { get { name }.isEqualTo("connectedAndroidTest") }
                }
            }

            @Test
            fun `depend on connected android test execution skipped`() {
                project.extra.set("coverageSkipTestTasks", null)
                project.forceCreateVariantsAndTasks()
                expectThat(jacocoConnectedTestReportTask().taskDependencies.getDependencies(jacocoConnectedTestReportTask())) {
                    isEmpty()
                }
            }

            @Test
            fun `include execution data`() {
                project.withDirectory("build/outputs/code_coverage/debugAndroidTest/connected") {
                    withFile("My Device-coverage.ec")
                }
                project.forceCreateVariantsAndTasks()
                expectThat(jacocoConnectedTestReportTask().executionData.files)
                    .containsExactly(
                        File(project.projectDir, "build/outputs/code_coverage/debugAndroidTest/connected/My Device-coverage.ec")
                    )
            }

            @Nested
            inner class `for type application` {
                @Test
                fun `add the default android unit tests report task for application`() {
                    project = AndroidProject(AndroidType.Application).withPlugin(JacocoAndroidConfigPlugin::class)
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
            inner class `for type library` {
                @Test
                fun `add the default android unit tests report task`() {
                    project = AndroidProject(AndroidType.Library).withPlugin(JacocoAndroidConfigPlugin::class)
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
            inner class `for type test` {
                @Test
                fun `add the default android unit tests report task`() {
                    val parent = RawProject().setup { withAndroidModule("app") }
                    project = AndroidProject(AndroidType.Test) { withParent(parent.project) }.withPlugin(JacocoAndroidConfigPlugin::class)
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

        @Nested
        inner class `for unit tests` {

            @Test
            fun `produce html report`() {
                project.forceCreateVariantsAndTasks()
                expectThat(jacocoUnitTestReportTask().reports.html) {
                    get { isEnabled }.isTrue()
                    get { destination }.isEqualTo(project.reporting.file("jacoco/testDebug/html"))
                }
            }

            @Test
            fun `xml report can be activated`() {
                project.forceCreateVariantsAndTasks()
                expectThat(jacocoUnitTestReportTask().reports.xml.isEnabled).isFalse()
                jacocoUnitTestReportTask().reports.xml.isEnabled = true
                expectThat(jacocoUnitTestReportTask().reports.xml) {
                    get { isEnabled }.isTrue()
                    get { destination }
                        .isEqualTo(project.reporting.file("jacoco/jacocoDebugUnitTestReport/jacocoDebugUnitTestReport.xml"))
                }
            }

            @Test
            fun `depend on android unit test execution`() {
                project.forceCreateVariantsAndTasks()
                expectThat(jacocoUnitTestReportTask().taskDependencies.getDependencies(jacocoUnitTestReportTask())) {
                    one { get { name }.isEqualTo("testDebugUnitTest") }
                }
            }

            @Test
            fun `depend on android unit test execution skipped`() {
                project.extra.set("coverageSkipTestTasks", null)
                project.forceCreateVariantsAndTasks()
                expectThat(jacocoUnitTestReportTask().taskDependencies.getDependencies(jacocoUnitTestReportTask())) {
                    isEmpty()
                }
            }

            @Test
            fun `include execution data`() {
                project.withDirectory("build/jacoco") {
                    withFile("testDebugUnitTest.exec")
                }
                project.forceCreateVariantsAndTasks()
                expectThat(jacocoUnitTestReportTask().executionData.files)
                    .containsExactly(File(project.projectDir, "build/jacoco/testDebugUnitTest.exec"))
            }

            @Nested
            inner class `for type application` {
                @Test
                fun `add the default android unit tests report task for application`() {
                    project = AndroidProject(AndroidType.Application).withPlugin(JacocoAndroidConfigPlugin::class)
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
            inner class `for type library` {
                @Test
                fun `add the default android unit tests report task`() {
                    project = AndroidProject(AndroidType.Library).withPlugin(JacocoAndroidConfigPlugin::class)
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
    }

    private fun jacocoAndroidExtension() = project.extensions.getByType(JacocoAndroidExtension::class.java)
    private fun jacocoReportTask() = project.tasks.getByName("jacocoTestReport")
    private fun jacocoConnectedTestReportTask() = project.tasks.withType(JacocoConnectedAndroidTestReport::class.java).single()
    private fun jacocoUnitTestReportTask() = project.tasks.withType(JacocoAndroidUnitTestReport::class.java).single()
}