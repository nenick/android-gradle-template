package de.nenick.gradle.plugins.jacoco.android

import de.nenick.gradle.plugins.jacoco.android.wrapper.AndroidExtension
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.internal.reflect.Instantiator
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import javax.inject.Inject

/**
 * Apply and configures jacoco for a common kotlin module.
 */
open class JacocoAndroidConfigPlugin @Inject constructor(instantiator: Instantiator) : JacocoPlugin(instantiator) {
    override fun apply(target: ProjectInternal) {
        super.apply(target)
        val jacocoTestReportTask = target.tasks.register("jacocoTestReport").get()
        val settings = target.extensions.create("jacocoAndroid", JacocoAndroidExtension::class.java)
        val androidExtension = AndroidExtension.wrap(target.extensions)

        androidExtension.variants().all {

            // TODO support only single buildType but all productFlavors?
            // TODO read variant from testBuildType?
            if (buildType.name == settings.connectedAndroidTests.variantForCoverage) {
                target.tasks.register(
                    "jacoco${name.capitalize()}ConnectedTestReport",
                    JacocoConnectedAndroidTestReport::class.java
                ).also {
                    jacocoTestReportTask.dependsOn(it)
                }
            }

            if (name == settings.androidUnitTests.variantForCoverage) {
                target.tasks.register(
                    "jacoco${name.capitalize()}UnitTestReport",
                    JacocoAndroidUnitTestReport::class.java
                ).also {
                    jacocoTestReportTask.dependsOn(it)
                }
            }
        }
    }
}