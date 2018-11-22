package com.template.project.data.local.tools

import androidx.room.Room
import org.koin.dsl.module.module

val dataLocalModule = module {
    single { Room.databaseBuilder(get(), ProjectDatabase::class.java, "project_database").build() }
    single { get<ProjectDatabase>().todo() }
}