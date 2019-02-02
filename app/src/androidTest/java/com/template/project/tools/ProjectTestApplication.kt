package com.template.project.tools

import com.template.project.ProjectApplication
import org.koin.dsl.module.Module

class ProjectTestApplication: ProjectApplication() {

    /** Force an error when any dependency isn't mocked. */
    override fun modulesDependencies() = emptyList<Module>()
}
