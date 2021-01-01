package de.nenick.gradle.test.tools.project

import org.gradle.testfixtures.ProjectBuilder

class RawProject(initialSetup: ProjectBuilder.() -> Unit = {}) : ProjectSetup<RawProject>(initialSetup)