package de.nenick.gradle.test.tools.project

import org.gradle.testfixtures.ProjectBuilder

class KotlinProject(projectDefinition: ProjectBuilder.() -> Unit = {}) : ProjectSetup<KotlinProject>(projectDefinition) {
    init {
        withPlugin("kotlin")
    }
}