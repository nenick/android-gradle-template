package com.template.project.data.local.tools

import com.template.project.data.local.ProjectDatabase
import org.koin.dsl.module.module

val dataLocalModule = module {
    single { ProjectDatabase.getDatabase(get()).todo() }
}