package com.template.project.data.local.tools

import androidx.room.Database
import androidx.room.RoomDatabase
import com.template.project.data.local.TodoDao
import com.template.project.data.local.entities.Todo

@Database(entities = [Todo::class], version = 1)
abstract class ProjectDatabase : RoomDatabase() {

    abstract fun todo(): TodoDao
}
