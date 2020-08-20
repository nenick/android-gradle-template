package de.nenick.gradle.plugins.jacoco.android

import com.android.build.gradle.internal.tasks.factory.dependsOn
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

            target.tasks.register("jacocoTestReport").dependsOn("connectedDebugAndroidTest")

    }
}