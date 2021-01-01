package de.nenick.gradle.tools

// ########## Copy from kotlin-dsl ##########
// We could not find a way to make this extensions available from the kotlin-dsl yet. But they are very helpful.

/**
 * Retrieves the [reporting][org.gradle.api.reporting.ReportingExtension] extension.
 */
val org.gradle.api.Project.reporting: org.gradle.api.reporting.ReportingExtension get() =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.getByName("reporting") as org.gradle.api.reporting.ReportingExtension